package com.example.sales_service.model;

public class S3FileMetadata {
    private final String key;
    private final String contentType;

    public S3FileMetadata(String key, String contentType) {
        this.key = key;
        this.contentType = contentType;
    }

    public String getKey() {
        return key;
    }

    public String getContentType() {
        return contentType;
    }
}
