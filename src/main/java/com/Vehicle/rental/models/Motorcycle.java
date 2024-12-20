package com.Vehicle.rental.models;

import com.Vehicle.rental.Exceptions.VehicleNotAvailable;
import com.Vehicle.rental.services.Rentable;

import java.util.UUID;

public non-sealed class Motorcycle extends Vehicle implements Rentable {

    private int engineCapacity;

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public Motorcycle(UUID vehicleId, String model, double baseRentalRate, boolean isAvailable, int engineCapacity) {
        super(vehicleId, model, baseRentalRate, isAvailable);
        this.engineCapacity = engineCapacity;
    }

    @Override
    public double calculateRentalCost(int daysRented) {
        double baseRentalRate = getBaseRentalRate();
        double surcharge = engineCapacity * 15 * daysRented; //*15 for everyday of use
        return baseRentalRate + surcharge;
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
                ", engineCapacity=" + engineCapacity +
                '}';
    }

    @Override
    public double rent(Customer customer, int days) {
        if(!isAvailableForRental()) {
            throw new VehicleNotAvailable("This motorcycle is not available for rent");
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
        return isAvailableForRental();
    }
}
