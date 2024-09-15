package com.example.report_service.model.template.fieldGroup;

import java.util.List;

public class FieldGroup {
    private String title;
    private List<Column> columns;

    public String getTitle() {
        return title;
    }

    public List<Column> getColumns() {
        return columns;
    }

    @Override
    public String toString() {
        return "FieldGroup{" +
                "title='" + title + '\'' +
                ", columns=" + columns +
                '}';
    }
}
