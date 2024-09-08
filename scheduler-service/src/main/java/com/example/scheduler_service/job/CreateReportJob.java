package com.example.scheduler_service.job;

import com.example.scheduler_service.service.ProducerService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateReportJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(CreateReportJob.class);

    private final ProducerService producerService;
    public CreateReportJob(ProducerService producerService) {
        this.producerService = producerService;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("CreateReportJob is executing!");
        producerService.sendMessage("This message is sent from scheduler-service");
    }
}
