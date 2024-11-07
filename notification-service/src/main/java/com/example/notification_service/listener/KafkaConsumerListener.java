package com.example.notification_service.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerListener {

    final Logger logger = LoggerFactory.getLogger(getClass());

    @KafkaListener(topics = "report-ready-request", groupId = "${spring.kafka.consumer.group-id}")
    public void listenAccessLinkResponse(String message) {
        final String consumeMessage = String.format("Received Message: [%s] ", message);
        logger.info(consumeMessage);
        logger.info("14-Listen: sales-data-response");
        //TODO: Send email notification
        logger.info("15-email-notification");
    }
}
