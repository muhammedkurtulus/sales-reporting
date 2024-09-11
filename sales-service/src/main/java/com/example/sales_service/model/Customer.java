package com.example.sales_service.model;

public class Customer {
    private String gender;
    private int age;
    private String email;
    private int satisfaction;

    // Getters and Setters

    @Override
    public String toString() {
        return "Customer{" +
                "gender='" + gender + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", satisfaction=" + satisfaction +
                '}';
    }
}
