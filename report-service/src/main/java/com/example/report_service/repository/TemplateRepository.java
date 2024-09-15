package com.example.report_service.repository;

import com.example.report_service.model.template.Template;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TemplateRepository extends MongoRepository<Template, String> {
    Optional<Template> findByTemplateName(String templateName);
}

