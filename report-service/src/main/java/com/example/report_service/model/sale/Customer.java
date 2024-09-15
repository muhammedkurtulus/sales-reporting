package com.example.report_service.model.sale;

public class Customer {
    private String gender;
    private int age;
    private String email;
    private int satisfaction;

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public int getSatisfaction() {
        return satisfaction;
    }

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
