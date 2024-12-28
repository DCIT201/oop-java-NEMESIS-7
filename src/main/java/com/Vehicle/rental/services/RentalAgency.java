package com.Vehicle.rental.services;

import com.Vehicle.rental.Exceptions.*;
import com.Vehicle.rental.models.Customer;
import com.Vehicle.rental.models.RentalTransaction;
import com.Vehicle.rental.models.Vehicle;

import static com.Vehicle.rental.models.RentalTransaction.RentalStatus.*;
import java.util.*;
import java.util.stream.Collectors;

public class RentalAgency {
    private final Map<UUID, Vehicle> carFleet;
    private final Map<UUID, RentalTransaction> transactions;
    private final Map<UUID, RentalTransaction> completedOrCancelledTransactions;
    private final Map<UUID, Customer> customers;

    public RentalAgency() {
        this.carFleet = new HashMap<>();
        this.transactions = new HashMap<>();
        this.completedOrCancelledTransactions = new HashMap<>();
        this.customers = new HashMap<>();
    }

    public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Please enter a valid customer");
        }
        if(!customers.containsKey(customer.getCustomerId())){
            customers.put(customer.getCustomerId(), customer);
        }
    }
    public List<Customer> getCustomers() {
        return customers
                .values()
                .stream()
                .toList();
    }
    public RentalTransaction rentVehicle(UUID vehicleId, Customer customer, int daysRented) throws CustomerDoesNotExist, VehicleDoesNotExist {
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
        }else {
            throw new RecordDoesNotExist("No vehicle rented.");
        }
    }
    public void addToFleet(Vehicle vehicle) {
        if (!carFleet.containsKey(vehicle.getVehicleId())) {
            carFleet.put(vehicle.getVehicleId(), vehicle);
        }else{
            throw new VehicleAlreadyExists("The vehicle you are adding to the fleet already exists.");
        }
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

    public Optional<Vehicle> getVehicle(String model) {
        return carFleet
                .values()
                .stream()
                .filter(vehicle -> vehicle.getModel().equals(model))
                .findFirst();
    }

    public Optional<Customer> findCustomerByEmail(String email) {
        return customers
                .values()
                .stream()
                .filter(customer -> customer.getEmail().equals(email))
                .findFirst();
    }

    public void rateVehicle(UUID vehicleId, int rating) {
        Vehicle vehicle = carFleet.get(vehicleId);
        vehicle.addRating(rating);
    }
     public List<String> getAvailableTypeVehicles() {
        return carFleet
                .values()
                .stream()
                .filter(Vehicle::isAvailable)
                .map(vehicle -> "Model: " + vehicle.getModel()
                + "\nBase Rental Rate: " + vehicle.getBaseRentalRate())
                .toList();
     }
}