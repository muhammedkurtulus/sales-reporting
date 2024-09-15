package com.example.report_service.listener;

import com.example.report_service.service.CreateReportService;
import com.example.report_service.service.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerListener {

    final Logger logger = LoggerFactory.getLogger(getClass());
    private final ProducerService producerService;
    private final CreateReportService createReportService;

    public KafkaConsumerListener(ProducerService producerService, CreateReportService createReportService) {
        this.producerService = producerService;
        this.createReportService = createReportService;
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
    public void listenSalesDataResponse(String message) {
        final String consumeMessage = String.format("Received Message: [%s] ", message);
        logger.info(consumeMessage);
        logger.info("7-Listen: sales-data-response");

        createReportService.createReport(message);
    }

    @KafkaListener(topics = "access-link-response", groupId = "${spring.kafka.consumer.group-id}")
    public void listenAccessLinkResponse(String message) {
        final String consumeMessage = String.format("Received Message: [%s] ", message);
        logger.info(consumeMessage);
        logger.info("14-Listen: access-link-response");
        producerService.sendMessage("report-ready-request", "report-ready-request");
        logger.info("15-report-ready-request");
    }
}
