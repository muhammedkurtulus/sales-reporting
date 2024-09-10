package com.example.scheduler_service.job;

import com.example.scheduler_service.service.CreateReportService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateReportJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(CreateReportJob.class);

    private final CreateReportService createReportService;

    public CreateReportJob(CreateReportService createReportService) {
        this.createReportService = createReportService;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("CreateReportJob is executing!");
        createReportService.createReport();
    }
}
