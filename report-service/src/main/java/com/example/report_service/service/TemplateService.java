package com.example.report_service.service;

import com.example.report_service.model.template.Template;
import com.example.report_service.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TemplateService {

    private final TemplateRepository templateRepository;

    @Autowired
    public TemplateService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public Optional<Template> getTemplateByName(String templateName) {
        return templateRepository.findByTemplateName(templateName);
    }
}
