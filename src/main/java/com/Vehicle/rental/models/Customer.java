package com.Vehicle.rental.models;

import java.util.*;

public class Customer {
    private final UUID customerId;
    private String customerName;
    private String email;
    private String phone;
    private int loyaltyPoints;
    private final Map<UUID, RentalTransaction> rentalHistory;
    private List<Integer> ratings;


    public Customer(String customerName, String phone, String email) {
        this.customerId = UUID.randomUUID();
        this.loyaltyPoints = 0;
        this.customerName = customerName;
        this.phone = phone;
        this.email = email;
        this.rentalHistory = new HashMap<>();
        this.ratings = new ArrayList<>();
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

    public UUID getCustomerId() {
        return customerId;
    }

    public List<Integer> getRatings() {
        return ratings;
    }
    public void setRatings(List<Integer> ratings) {
        this.ratings = ratings;
    }

    public void addRating(int rating) {
        if(rating < 0 || rating > 5){
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }
        ratings.add(rating);
    }

    public double getAverageRating() {
        return ratings
                .stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    public int getNumberOfRatings() {
        return ratings.size();
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
                ", ratings=" + ratings +
                '}';
    }
}
