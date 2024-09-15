package com.example.report_service.service;

import com.example.report_service.model.sale.Sale;
import com.example.report_service.model.template.Template;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CreateReportService {
    final Logger logger = LoggerFactory.getLogger(getClass());
    private final ProducerService producerService;
    private final TemplateService templateService;
    private final ExcelReportService excelReportService;
    private final ObjectMapper objectMapper;

    public CreateReportService(ProducerService producerService, TemplateService templateService, ExcelReportService excelReportService, ObjectMapper objectMapper) {
        this.producerService = producerService;
        this.templateService = templateService;
        this.excelReportService = excelReportService;
        this.objectMapper = objectMapper;
    }

    public void createReport(String message) {
        String templateName = "excel";

        try {
            // Parse the JSON message to extract Sales data
            List<Sale> sales = objectMapper.readValue(message, new TypeReference<List<Sale>>() {
            });

            Optional<Template> template = templateService.getTemplateByName(templateName);
            logger.info("Template: {}", template.toString());

            logger.info("8-Get Report Template");

            logger.info("sales data {}", sales.getFirst().toString());

            byte[] byteArray = excelReportService.generateReport(template, sales);

            logger.info("9-Create Report");

            producerService.sendMessage("save-report-request", Arrays.toString(byteArray));

            logger.info("10-save-report-request");
        } catch (IOException e) {
            logger.error("Error processing report generation", e);
        }
    }
}
