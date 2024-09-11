package com.example.sales_service.model;

import java.math.BigDecimal;
import java.util.List;

public class Item {
    private String name;
    private List<String> tags;
    private BigDecimal price;
    private int quantity;

    // Getters and Setters

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
