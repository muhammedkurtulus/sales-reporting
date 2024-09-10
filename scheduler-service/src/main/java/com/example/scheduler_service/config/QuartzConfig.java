package com.example.scheduler_service.config;

import com.example.scheduler_service.job.CreateReportJob;
import org.quartz.*;
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

    public Trigger createTrigger(String scheduleType, String dayOfWeek, int hour, int minute) {
        Trigger trigger = null;
        if ("daily".equalsIgnoreCase(scheduleType)) {
            trigger = TriggerBuilder.newTrigger()
                    .forJob(createReportJobDetail())
                    .withIdentity("createReportDailyTrigger")
                    .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(hour, minute))
                    .build();
        } else if ("weekly".equalsIgnoreCase(scheduleType) && dayOfWeek != null) {
            trigger = TriggerBuilder.newTrigger()
                    .forJob(createReportJobDetail())
                    .withIdentity("createReportWeeklyTrigger")
                    .withSchedule(CronScheduleBuilder.weeklyOnDayAndHourAndMinute(
                            getDayOfWeek(dayOfWeek), hour, minute))
                    .build();
        }
        return trigger;
    }

    private int getDayOfWeek(String day) {
        return switch (day.toUpperCase()) {
            case "SUN" -> DateBuilder.SUNDAY;
            case "MON" -> DateBuilder.MONDAY;
            case "TUE" -> DateBuilder.TUESDAY;
            case "WED" -> DateBuilder.WEDNESDAY;
            case "THU" -> DateBuilder.THURSDAY;
            case "FRI" -> DateBuilder.FRIDAY;
            case "SAT" -> DateBuilder.SATURDAY;
            default -> throw new IllegalArgumentException("Invalid day: " + day);
        };
    }
}
