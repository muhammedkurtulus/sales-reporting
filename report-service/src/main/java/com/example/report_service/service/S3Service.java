package com.example.report_service.service;

import com.example.report_service.model.S3FileMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final ObjectMapper objectMapper;

    @Value("${spring.aws.s3.bucket-name}")
    private String bucketName;

    public S3Service(S3Client s3Client, ObjectMapper objectMapper) {
        this.s3Client = s3Client;
        this.objectMapper = objectMapper;
    }

    public S3FileMetadata uploadObject(byte[] fileData, String key, String contentType) throws IOException {

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(contentType)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileData));

        return new S3FileMetadata(key, contentType);
    }

    public String downloadObject(String key) throws IOException {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        return s3Client.getObject(getObjectRequest, ResponseTransformer.toBytes()).asUtf8String();
    }
}
