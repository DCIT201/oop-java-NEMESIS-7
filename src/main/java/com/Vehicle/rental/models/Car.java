package com.Vehicle.rental.models;


public final class Car extends Vehicle{

    private boolean hasClimateControl;

    public boolean isHasClimateControl() {
        return hasClimateControl;
    }

    public void setHasClimateControl(boolean hasClimateControl) {
        this.hasClimateControl = hasClimateControl;
    }

    public Car(String vehicleId, String model, double baseRentalRate, boolean isAvailable, boolean hasClimateControl) {
        super(vehicleId, model, baseRentalRate, isAvailable);
        this.hasClimateControl = hasClimateControl;
    }


    @Override
    public double calculateRentalCost(int days) {
        if (hasClimateControl) {
            return getBaseRentalRate() * days;
        }
        return getBaseRentalRate();

    }

    @Override
    public boolean isAvailableForRental() {
        return isAvailable();
    }

}
