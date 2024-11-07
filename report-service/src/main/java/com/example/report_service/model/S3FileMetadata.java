package com.example.report_service.model;

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

    @Override
    public String toString() {
        return "S3FileMetadata{" +
                "key='" + key + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
