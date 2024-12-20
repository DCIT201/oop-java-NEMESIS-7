package com.Vehicle.rental.models;


import com.Vehicle.rental.Exceptions.VehicleNotAvailable;
import com.Vehicle.rental.services.Rentable;

import java.util.UUID;

public non-sealed class Car extends Vehicle implements Rentable {

    private boolean hasClimateControl;

    public boolean isHasClimateControl() {
        return hasClimateControl;
    }

    public void setHasClimateControl(boolean hasClimateControl) {
        this.hasClimateControl = hasClimateControl;
    }

    public Car(UUID vehicleId, String model, double baseRentalRate, boolean isAvailable, boolean hasClimateControl) {
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
    @Override
    public String toString() {
        return "Car{" +
                "vehicleId='" + getVehicleId() + '\'' +
                ", model='" + getModel() + '\'' +
                ", baseRentalRate=" + getBaseRentalRate() +
                ", isAvailable=" + isAvailable() +
                ", hasClimateControl=" + hasClimateControl +
                '}';
    }

    @Override
    public double rent(Customer customer, int days) {
        if(!isAvailableForRental()) {
            throw new VehicleNotAvailable("This car is not available for rent");
        }
        setAvailable(false);
        return calculateRentalCost(days);

    }

    @Override
    public void returnVehicle() {
        setAvailable(true);
    }

    @Override
    public boolean isAvailableForRent() {
        return isAvailable();
    }
}
