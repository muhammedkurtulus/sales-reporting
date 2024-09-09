package com.example.scheduler_service.config;

import com.example.scheduler_service.job.CreateReportJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail createReportJobDetail() {
        return JobBuilder.newJob(CreateReportJob.class)
                .withIdentity("createReportJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger simpleJobTrigger() {
        //TODO: will configure later
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(120)
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(createReportJobDetail())
                .withIdentity("createReportJobTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
