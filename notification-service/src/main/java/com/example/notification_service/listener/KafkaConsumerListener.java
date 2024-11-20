package com.example.notification_service.listener;

import com.example.notification_service.model.S3FileMetadata;
import com.example.notification_service.service.S3Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class KafkaConsumerListener {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private final S3Service s3Service;

    @Autowired
    private ObjectMapper objectMapper;

    public KafkaConsumerListener(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @KafkaListener(topics = "report-ready-request", groupId = "${spring.kafka.consumer.group-id}")
    public void listenReportReadyResponse(String message) throws IOException {
        final String consumeMessage = String.format("Received Message: [%s] ", message);
        logger.info(consumeMessage);
        logger.info("14-Listen: report-ready-response");

        S3FileMetadata metadata = objectMapper.readValue(message, S3FileMetadata.class);
        logger.info(metadata.getKey());

        byte[] fileContent = s3Service.downloadObject(metadata.getKey());
        logger.info("Downloaded Content: {}", fileContent);

        logger.info("15-Get Report");

        //TODO: Send email notification
        logger.info("16-email-notification");
    }
}
