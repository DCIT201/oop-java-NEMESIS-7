package com.Vehicle.rental.models;


import com.Vehicle.rental.Exceptions.VehicleAlreadyExists;
import com.Vehicle.rental.Exceptions.VehicleNotAvailable;
import com.Vehicle.rental.services.Rentable;

import java.util.Objects;
import java.util.UUID;

public non-sealed class Car extends Vehicle implements Rentable {

    private boolean hasClimateControl;

    public boolean isHasClimateControl() {
        return hasClimateControl;
    }

    public void setHasClimateControl(boolean hasClimateControl) {
        this.hasClimateControl = hasClimateControl;
    }

    public Car(String model, double baseRentalRate, boolean isAvailable, boolean hasClimateControl) {
        super(model, baseRentalRate, isAvailable);
        this.hasClimateControl = hasClimateControl;
    }


    @Override
    public double calculateRentalCost(int days) {
        double cost = getBaseRentalRate() * days;
        if (hasClimateControl) {
            cost +=  15 * days;
            return cost;
        }
        return getBaseRentalRate() * days;

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
        if(isAvailable()) {
            throw new VehicleAlreadyExists("This car is already available");
        }
        setAvailable(true);
    }

    @Override
    public boolean isAvailableForRent() {
        return isAvailable();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return hasClimateControl == car.hasClimateControl;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(hasClimateControl);
    }
}
