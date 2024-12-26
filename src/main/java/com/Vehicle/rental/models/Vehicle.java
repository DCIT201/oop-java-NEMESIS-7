package com.Vehicle.rental.models;

import com.Vehicle.rental.services.Rentable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public abstract sealed class Vehicle implements Rentable permits Car, Truck, Motorcycle {
    private final UUID vehicleId;

    private String model;
    private double baseRentalRate;
    private boolean isAvailable;

    private List<Integer> ratings = new ArrayList<>();

    public abstract double calculateRentalCost(int daysRented);
    public abstract boolean isAvailableForRental();


    public void addRating(int rating) {
        if(rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }
        ratings.add(rating);
    }
    public double getRating(){
        return ratings
                .stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    public double getNumberOfRatings(){
        return ratings.size();
    }
//    abstract Function<Double, Double> calculateRentalCost(int days);

    public Vehicle(String model, double baseRentalRate, boolean isAvailable) {
        this.vehicleId = UUID.randomUUID();
        this.model = model;
        this.baseRentalRate = baseRentalRate;
        this.isAvailable = isAvailable;
    }

    public UUID getVehicleId() {
        return vehicleId;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getBaseRentalRate() {
        return baseRentalRate;
    }

    public void setBaseRentalRate(double baseRentalRate) {
        this.baseRentalRate = baseRentalRate;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Double.compare(baseRentalRate, vehicle.baseRentalRate) == 0 && isAvailable == vehicle.isAvailable && Objects.equals(vehicleId, vehicle.vehicleId) && Objects.equals(model, vehicle.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleId, model, baseRentalRate, isAvailable);
    }

    public List<Integer> getRatings() {
        return ratings;
    }

    public void setRatings(List<Integer> ratings) {
        this.ratings = ratings;
    }
}