package com.example.report_service.model.sale;

import java.math.BigDecimal;
import java.util.List;

public class Item {
    private String name;
    private List<String> tags;
    private BigDecimal price;
    private int quantity;

    public String getName() {
        return name;
    }

    public List<String> getTags() {
        return tags;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", tags=" + tags +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
