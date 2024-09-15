package com.example.report_service.model.template.fieldGroup;

public class Column {
    private String header;
    private String key;
    private String type;
    private String formula;

    public String getHeader() {
        return header;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public String getFormula() {
        return formula;
    }

    @Override
    public String toString() {
        return "Column{" +
                "header='" + header + '\'' +
                ", key='" + key + '\'' +
                ", type='" + type + '\'' +
                ", formula='" + formula + '\'' +
                '}';
    }
}
