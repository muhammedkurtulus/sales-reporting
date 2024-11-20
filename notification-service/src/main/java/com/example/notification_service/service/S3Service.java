package com.example.notification_service.service;

import com.example.notification_service.model.S3FileMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;

@Service
public class S3Service {

    private static final Logger logger = LoggerFactory.getLogger(S3Service.class);

    private final S3Client s3Client;
    private final ObjectMapper objectMapper;

    @Value("${spring.aws.s3.bucket-name}")
    private String bucketName;

    public S3Service(S3Client s3Client, ObjectMapper objectMapper) {
        this.s3Client = s3Client;
        this.objectMapper = objectMapper;
    }

    public S3FileMetadata uploadObject(byte[] fileData, String key, String contentType) throws IOException {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileData));
            return new S3FileMetadata(key, contentType);
        } catch (S3Exception e) {
            logger.error("Error uploading object to S3 with key {}: {}", key, e.getMessage());
            throw new IOException("Failed to upload file to S3", e);
        }
    }

    public byte[] downloadObject(String key) throws IOException {
        try {

            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            ResponseBytes<GetObjectResponse> responseBytes = s3Client.getObject(getObjectRequest, ResponseTransformer.toBytes());

            byte[] fileContent = responseBytes.asByteArray();

            String contentType = responseBytes.response().contentType();
            logger.info("Content-Type of file: {}", contentType);
            logger.info("File size: {} bytes", fileContent.length);

            return fileContent;
        } catch (S3Exception e) {
            logger.error("Error downloading object from S3 with key {}: {}", key, e.getMessage());
            throw new IOException("Failed to download file from S3", e);
        }
    }
}
