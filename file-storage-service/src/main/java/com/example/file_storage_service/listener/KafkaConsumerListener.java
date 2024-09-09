package com.example.file_storage_service.listener;


import com.example.file_storage_service.service.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerListener {

    final Logger logger = LoggerFactory.getLogger(getClass());
    private final ProducerService producerService;
    public KafkaConsumerListener(ProducerService producerService) {
        this.producerService = producerService;
    }

    @KafkaListener(topics = "save-report-request", groupId = "${spring.kafka.consumer.group-id}")
    public void listenCreateReportRequest(String message) {
        final String consumeMessage = String.format("Received Message: [%s] ", message);
        logger.info(consumeMessage);
        logger.info("11-Listen: create-report-request");
        //TODO: Save report to file storage
        logger.info("12-Save Report & Create Access Link");
        producerService.sendMessage("access-link-response", "access-link-response");
        logger.info("13-access-link-response");
    }

}
