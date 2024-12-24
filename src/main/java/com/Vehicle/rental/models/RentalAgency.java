package com.Vehicle.rental.models;

import com.Vehicle.rental.Exceptions.RecordDoesNotExist;
import com.Vehicle.rental.Exceptions.VehicleAlreadyExists;
import com.Vehicle.rental.Exceptions.VehicleDoesNotExist;
import com.Vehicle.rental.Exceptions.VehicleNotAvailable;
import static com.Vehicle.rental.models.RentalTransaction.RentalStatus.*;
import java.util.*;

public class RentalAgency {
    private final Map<UUID, Vehicle> carFleet;
    private final Map<UUID, RentalTransaction> transactions;
    private final Map<UUID, RentalTransaction> completedOrCancelledTransactions;

    public RentalAgency() {
        this.carFleet = new HashMap<>();
        this.transactions = new HashMap<>();
        this.completedOrCancelledTransactions = new HashMap<>();
    }

    public RentalTransaction rentVehicle(UUID vehicleId, Customer customer, int daysRented) {
        if(vehicleId == null || customer == null){
            throw new IllegalArgumentException("Vehicle id and customer are required");
        }
        if(daysRented <= 0){
            throw new IllegalArgumentException("Days rented must be greater than 0");
        }

        if (!carFleet.containsKey(vehicleId)) {
            throw new VehicleDoesNotExist("The vehicle you requested does not exist");
        }
        Vehicle vehicle = carFleet.get(vehicleId);
        if (vehicle.isAvailableForRental()) {
            vehicle.rent(customer, daysRented);
        } else {
            throw new VehicleNotAvailable("The vehicle you requested is not available for renting.");
        }
        double rentalCost = calculateRentalCost(vehicle, customer, daysRented);
        RentalTransaction transaction = new RentalTransaction(
                vehicle,
                customer,
                daysRented,
                rentalCost,
                ONGOING

        );
        transactions
                .put(transaction
                                .getTransactionId(),
                        transaction
                );
        return transaction;
    }

    public double calculateRentalCost(Vehicle vehicle, Customer customer, int daysRented) {
        if(customer.getLoyaltyPoints() > 0) {
            return vehicle.calculateRentalCost(daysRented) / customer.getLoyaltyPoints() * 0.01;
        }else{
            return vehicle.calculateRentalCost(daysRented);
        }
    }

    public void returnVehicle(UUID rentalTransactionId) {
        if (!transactions.containsKey(rentalTransactionId)) {
            throw new RecordDoesNotExist("There's no record of rental.");
        }
        RentalTransaction transaction = transactions.get(rentalTransactionId);
        Vehicle vehicle = transaction.getRentedVehicle();
        Customer customer = transaction.getCustomer();
        if(vehicle != null) {
            vehicle.setAvailable(true);
            customer.setLoyaltyPoints(customer.getLoyaltyPoints() + 1);
            finaliseTransaction(rentalTransactionId, RETURNED);
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
    public void cancelRental(UUID transactionId) {
        if(transactionId == null) {
            throw new IllegalArgumentException("The transaction id is required");
        }
        if (!transactions.containsKey(transactionId)) {
            throw new RecordDoesNotExist("There's no record of rental.");
        }
        finaliseTransaction(transactionId, CANCELLED);
    }

    private void finaliseTransaction(UUID transactionId, RentalTransaction.RentalStatus status) {
        RentalTransaction transaction = transactions.remove(transactionId);
        if(transaction != null) {
            transaction.setStatus(status);
            completedOrCancelledTransactions.put(transactionId, transaction);
        }
    }

}