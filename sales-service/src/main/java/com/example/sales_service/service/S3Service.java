package com.example.sales_service.service;

import com.example.sales_service.model.S3FileMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
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

    public S3FileMetadata uploadObject(Object data, String key) throws IOException {

        String jsonContent = objectMapper.writeValueAsString(data);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromString(jsonContent));

        String contentType = "application/json";

        return new S3FileMetadata(key, contentType);
    }
}
