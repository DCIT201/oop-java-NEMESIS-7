package com.Vehicle.rental.models;

public final class Truck extends Vehicle{

    private double loadCapacity;

    public double getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(double loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    public Truck(String vehicleId, String model, double baseRentalRate, boolean isAvailable, double loadCapacity) {
        super(vehicleId, model, baseRentalRate, isAvailable);
        this.loadCapacity = loadCapacity;
    }


    @Override
    public double calculateRentalCost(int daysRented) {
        double basePrice = getBaseRentalRate();
        return basePrice + (daysRented * loadCapacity);
    }

    @Override
    public boolean isAvailableForRental() {
        return isAvailable();
    }
}
