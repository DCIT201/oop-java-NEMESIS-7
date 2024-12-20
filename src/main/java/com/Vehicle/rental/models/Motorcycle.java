package com.Vehicle.rental.models;

public final class Motorcycle extends Vehicle{

    private int engineCapacity;

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public Motorcycle(String vehicleId, String model, double baseRentalRate, boolean isAvailable, int engineCapacity) {
        super(vehicleId, model, baseRentalRate, isAvailable);
        this.engineCapacity = engineCapacity;
    }

    @Override
    public double calculateRentalCost(int daysRented) {
        double baseRentalRate = getBaseRentalRate();
        return baseRentalRate + (engineCapacity * daysRented);
    }

    @Override
    public boolean isAvailableForRental() {
        return isAvailable();
    }
}
