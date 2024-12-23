package com.Vehicle.rental.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RentalTransactionTest {

    private RentalTransaction rentalTransaction;
    private Customer customer;
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        customer = new Customer("John Doe", "John Doe", "john@example.com");
        vehicle = new Motorcycle(UUID.randomUUID(), "Harley", 50.0, true, 500);
        rentalTransaction = new RentalTransaction(vehicle, customer, 25, 5.80, RentalTransaction.RentalStatus.ONGOING);
    }

    @Test
    void testGetTotalCost() {
        double cost = rentalTransaction.getTotalPrice();
        assertEquals(50.0 + 500 * 0.2 * 5, cost);
    }

    @Test
    void testGetStatus() {
        assertEquals(RentalTransaction.RentalStatus.ONGOING, rentalTransaction.getStatus());
    }
}
