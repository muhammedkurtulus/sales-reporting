package com.example.sales_service.listener;

import com.example.sales_service.service.SalesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerListener {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SalesService salesService;

    @KafkaListener(topics = "sales-data-request", groupId = "${spring.kafka.consumer.group-id}")
    public void listenSalesDataResponse(String message) {
        final String consumeMessage = String.format("Received Message: [%s] ", message);
        logger.info(consumeMessage);
        logger.info("4-Listen: sales-data-request");

        salesService.setSalesData();
    }
}
