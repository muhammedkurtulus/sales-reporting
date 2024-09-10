package com.example.scheduler_service.service;

import com.example.scheduler_service.service.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateReportService {

    private static final Logger logger = LoggerFactory.getLogger(CreateReportService.class);
    private final ProducerService producerService;

    public CreateReportService(ProducerService producerService) {
        this.producerService = producerService;
    }

    public String createReport() {
        producerService.sendMessage("create-report-request", "create-report-request");
        logger.info("1-create-report-request");
        return "OK";
    }
}
