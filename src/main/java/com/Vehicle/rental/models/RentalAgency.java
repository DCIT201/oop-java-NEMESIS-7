package com.Vehicle.rental.models;

import com.Vehicle.rental.Exceptions.VehicleAlreadyExists;
import com.Vehicle.rental.Exceptions.VehicleDoesNotExist;
import com.Vehicle.rental.Exceptions.VehicleNotAvailable;

import java.util.*;

public class RentalAgency {
    private final Map<UUID, Vehicle> carFleet;
    private final Map<UUID, RentalTransaction> transactions;

    public RentalAgency() {
        this.carFleet = new HashMap<>();
        this.transactions = new HashMap<>();
    }

    public RentalTransaction rentVehicle(UUID vehicleId, Customer customer, int daysRented) {
        if (!carFleet.containsKey(vehicleId)) {
            throw new VehicleDoesNotExist("The vehicle you requested does not exist");
        }
        Vehicle vehicle = carFleet.get(vehicleId);
        if (vehicle.isAvailableForRental()) {
            vehicle.rent(customer, daysRented);
        } else {
            throw new VehicleNotAvailable("The vehicle you requested is not available for renting.");
        }
        RentalTransaction transaction = new RentalTransaction(
                vehicle,
                customer,
                daysRented,
                customer.getLoyaltyPoints() > 0 ?
                        (vehicle.calculateRentalCost(daysRented)) / (customer.getLoyaltyPoints() * 0.01)
                        :
                        vehicle.calculateRentalCost(daysRented),
                RentalTransaction.RentalStatus.ONGOING

        );
        transactions
                .put(
                        transaction.
                        getRentedVehicle().
                        getVehicleId(),
                transaction
                );
        return transaction;
    }

    public void returnVehicle(UUID rentalTransactionId) {
        if (!transactions.containsKey(rentalTransactionId)) {
            throw new VehicleDoesNotExist("There's no record of rental.");
        }
        RentalTransaction transaction = transactions.get(rentalTransactionId);
        Vehicle vehicle = transaction.getRentedVehicle();
        Customer customer = transaction.getCustomer();
        if(vehicle != null) {
            vehicle.setAvailable(true);
            transaction.setStatus(RentalTransaction.RentalStatus.RETURNED);
            customer.setLoyaltyPoints(customer.getLoyaltyPoints() + 1);
        }
    }
    public Vehicle addToFleet(Vehicle vehicle) {
        if (!carFleet.containsKey(vehicle.getVehicleId())) {
            carFleet.put(vehicle.getVehicleId(), vehicle);
        }else{
            throw new VehicleAlreadyExists("The vehicle you are adding to the fleet already exists.");
        }
        return vehicle;
    }
    public List<Vehicle> getFleet() {
        return carFleet
                .values()
                .stream()
                .toList();
    }
    public List<Vehicle> getAvailableVehicles() {
        return carFleet
                .values()
                .stream()
                .filter(Vehicle::isAvailableForRental)
                .toList();
    }

}
