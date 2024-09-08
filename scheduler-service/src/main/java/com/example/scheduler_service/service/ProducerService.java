package com.example.scheduler_service.service;

import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topicName;

    public ProducerService(Environment environment, KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = environment.getProperty("spring.kafka.producer.topic");
    }

    public void sendMessage(String msg) {
        kafkaTemplate.send(topicName, msg);
    }
}
