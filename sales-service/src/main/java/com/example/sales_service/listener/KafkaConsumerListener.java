package com.example.sales_service.listener;

import com.example.sales_service.model.S3FileMetadata;
import com.example.sales_service.service.ProducerService;
import com.example.sales_service.service.SalesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerListener {

    final Logger logger = LoggerFactory.getLogger(getClass());

    private final ProducerService producerService;

    @Autowired
    private SalesService salesService;

    @Autowired
    private ObjectMapper objectMapper;

    public KafkaConsumerListener(ProducerService producerService) {
        this.producerService = producerService;
    }

    @KafkaListener(topics = "sales-data-request", groupId = "${spring.kafka.consumer.group-id}")
    public void listenSalesDataResponse(String message) throws JsonProcessingException {
        final String consumeMessage = String.format("Received Message: [%s] ", message);
        logger.info(consumeMessage);
        logger.info("4-Listen: sales-data-request");

        S3FileMetadata metadata = salesService.setSalesData();

        String jsonMetadata = objectMapper.writeValueAsString(metadata);

        producerService.sendMessage("sales-data-response", jsonMetadata);

        logger.info("7-sales-data-response: {}", "");
    }
}
