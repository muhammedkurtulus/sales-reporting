package com.example.report_service.listener;

import com.example.report_service.model.S3FileMetadata;
import com.example.report_service.service.CreateReportService;
import com.example.report_service.service.ProducerService;
import com.example.report_service.service.S3Service;
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
    private final ProducerService producerService;
    private final CreateReportService createReportService;
    private final S3Service s3Service;

    @Autowired
    private ObjectMapper objectMapper;

    public KafkaConsumerListener(ProducerService producerService, CreateReportService createReportService, S3Service s3Service) {
        this.producerService = producerService;
        this.createReportService = createReportService;
        this.s3Service = s3Service;
    }

    @KafkaListener(topics = "create-report-request", groupId = "${spring.kafka.consumer.group-id}")
    public void listenCreateReportRequest(String message) {
        final String consumeMessage = String.format("Received Message: [%s] ", message);
        logger.info(consumeMessage);
        logger.info("2-Listen: create-report-request");
        producerService.sendMessage("sales-data-request", "sales-data-request");
        logger.info("3-sales-data-request");
    }

    @KafkaListener(topics = "sales-data-response", groupId = "${spring.kafka.consumer.group-id}")
    public void listenSalesDataResponse(String message) throws IOException {
        final String consumeMessage = String.format("Received Message: [%s] ", message);
        logger.info(consumeMessage);

        S3FileMetadata metadata = objectMapper.readValue(message, S3FileMetadata.class);

        logger.info("8-Listen: sales-data-response");

        String fileContent = s3Service.downloadObject(metadata.getKey());
        logger.info("Downloaded Content: {}", fileContent);

        logger.info("9-Get Sales Data");

        byte[] byteArrayReport = createReportService.createReport(fileContent);

        S3FileMetadata reportMetadata = s3Service.uploadObject(byteArrayReport, "report.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        logger.info("12-save-report");

        String jsonMetadata = objectMapper.writeValueAsString(reportMetadata);

        producerService.sendMessage("report-ready-request", jsonMetadata);
        logger.info("13-report-ready-request");
    }
}
