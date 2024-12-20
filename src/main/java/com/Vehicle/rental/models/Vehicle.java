package com.Vehicle.rental.models;

import com.Vehicle.rental.services.Rentable;

import java.util.UUID;

public abstract sealed class Vehicle implements Rentable permits Car, Truck, Motorcycle {
    private final UUID vehicleId;

    private String model;
    private double baseRentalRate;
    private boolean isAvailable;

    public abstract double calculateRentalCost(int daysRented);
    public abstract boolean isAvailableForRental();

//    abstract Function<Double, Double> calculateRentalCost(int days);

    public Vehicle(UUID vehicleId, String model, double baseRentalRate, boolean isAvailable) {
        this.vehicleId = vehicleId;
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

}