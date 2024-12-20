package com.Vehicle.rental.models;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Customer {
    private final UUID customerId;
    private String customerName;
    private String email;
    private String phone;
    private int loyaltyPoints;
    private final Map<UUID, RentalTransaction> rentalHistory;

    public Customer(Map<UUID, RentalTransaction> rentalHistory, int loyaltyPoints, String phone, String email, String customerName, UUID customerId) {
        this.rentalHistory = new HashMap<>();
        this.loyaltyPoints = loyaltyPoints;
        this.phone = phone;
        this.email = email;
        this.customerName = customerName;
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", loyaltyPoints=" + loyaltyPoints +
                ", rentalHistory=" + rentalHistory +
                '}';
    }
}
