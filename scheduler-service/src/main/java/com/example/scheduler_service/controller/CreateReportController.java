package com.example.scheduler_service.controller;

import com.example.scheduler_service.config.QuartzConfig;
import com.example.scheduler_service.service.CreateReportService;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/create-report")
public class CreateReportController {

    private static final Logger logger = LoggerFactory.getLogger(CreateReportController.class);

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private QuartzConfig quartzConfig;

    @Autowired
    private CreateReportService createReportService;

    @PostMapping("/schedule")
    public String scheduleJob(@RequestParam String scheduleType,
                              @RequestParam(required = false) String dayOfWeek,
                              @RequestParam int hour,
                              @RequestParam int minute) {
        try {
            if ("weekly".equalsIgnoreCase(scheduleType) && dayOfWeek == null) {
                return "Please specify a valid day of the week for weekly schedule.";
            }
            Trigger trigger = quartzConfig.createTrigger(scheduleType, dayOfWeek, hour, minute);
            scheduler.scheduleJob(trigger);
            return "Create Report Job scheduled with type: " + scheduleType + " at " + hour + ":" + minute;
        } catch (SchedulerException e) {
            logger.error("Create Report Job scheduling failed!", e);
            return "Create Report Job scheduling failed!";
        }
    }

    @GetMapping()
    public String createReport() {
        return createReportService.createReport();
    }
}
