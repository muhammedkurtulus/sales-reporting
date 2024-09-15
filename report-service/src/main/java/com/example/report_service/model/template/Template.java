package com.example.report_service.model.template;

import com.example.report_service.model.template.fieldGroup.FieldGroup;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "report_templates")
public class Template {

    @Id
    private String id;
    private String templateName;
    private String description;
    private List<FieldGroup> fields;
    private String fileType;

    public String getId() {
        return id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getDescription() {
        return description;
    }

    public List<FieldGroup> getFields() {
        return fields;
    }

    public String getFileType() {
        return fileType;
    }

    @Override
    public String toString() {
        return "Template{" +
                "id='" + id + '\'' +
                ", templateName='" + templateName + '\'' +
                ", description='" + description + '\'' +
                ", fields=" + fields +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}
