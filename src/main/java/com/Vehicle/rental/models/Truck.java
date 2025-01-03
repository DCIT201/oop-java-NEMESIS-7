package com.Vehicle.rental.models;

import com.Vehicle.rental.Exceptions.VehicleNotAvailable;
import com.Vehicle.rental.services.Rentable;
import com.Vehicle.rental.services.VehicleService;

import java.util.Objects;
import java.util.UUID;

public non-sealed class Truck extends Vehicle implements Rentable {

    private double loadCapacity;

    public double getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(double loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    public Truck(String model, double baseRentalRate, boolean isAvailable, double loadCapacity) {
        super(model, baseRentalRate, isAvailable);
        this.loadCapacity = loadCapacity;
    }


    @Override
    public double calculateRentalCost(int daysRented) {
        return getBaseRentalRate() + (loadCapacity * daysRented * 0.5);
    }

    @Override
    public boolean isAvailableForRental() {
        return isAvailable();
    }
    @Override
    public String toString() {
        return "Truck{" +
                "vehicleId='" + getVehicleId() + '\'' +
                ", model='" + getModel() + '\'' +
                ", baseRentalRate=" + getBaseRentalRate() +
                ", isAvailable=" + isAvailable() +
                ", loadCapacity=" + loadCapacity +
                '}';
    }

    @Override
    public double rent(Customer customer, int days) {
        if(!isAvailable()) {
            throw new VehicleNotAvailable("This truck is not available for renting");
        }
        double rentalCost = calculateRentalCost(days);
        setAvailable(false);
        return rentalCost;
    }

    @Override
    public void returnVehicle() {
        setAvailable(true);
    }

    @Override
    public boolean isAvailableForRent() {
        return isAvailable();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Truck truck = (Truck) o;
        return Double.compare(loadCapacity, truck.loadCapacity) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), loadCapacity);
    }
}
