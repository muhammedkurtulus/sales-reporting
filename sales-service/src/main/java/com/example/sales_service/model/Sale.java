package com.example.sales_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "sales")
public class Sale {

    @Id
    private String id;
    private Date saleDate;
    private List<Item> items;
    private String storeLocation;
    private Customer customer;
    private boolean couponUsed;
    private String purchaseMethod;

    public String getId() {
        return id;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public List<Item> getItems() {
        return items;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public Customer getCustomer() {
        return customer;
    }

    public boolean isCouponUsed() {
        return couponUsed;
    }

    public String getPurchaseMethod() {
        return purchaseMethod;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id='" + id + '\'' +
                ", saleDate=" + saleDate +
                ", items=" + items +
                ", storeLocation='" + storeLocation + '\'' +
                ", customer=" + customer +
                ", couponUsed=" + couponUsed +
                ", purchaseMethod='" + purchaseMethod + '\'' +
                '}';
    }
}
