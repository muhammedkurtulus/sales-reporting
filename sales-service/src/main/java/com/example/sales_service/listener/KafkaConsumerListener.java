package com.example.sales_service.listener;


import com.example.sales_service.service.ProducerService;
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

    @KafkaListener(topics = "sales-data-request", groupId = "${spring.kafka.consumer.group-id}")
    public void listenSalesDataResponse(String message) {
        final String consumeMessage = String.format("Received Message: [%s] ", message);
        logger.info(consumeMessage);
        logger.info("4-Listen: sales-data-request");
        //TODO: Get sales data from db
        logger.info("5-Get Sales Data");
        producerService.sendMessage("sales-data-response", "sales-data-response");
        logger.info("6-sales-data-response");
    }
}
