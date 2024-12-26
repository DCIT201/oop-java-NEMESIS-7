package com.Vehicle.rental.models;

import com.Vehicle.rental.services.RentalAgency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RentalTransactionTest {

    private RentalTransaction rentalTransaction;
    private Customer customer;
    private Vehicle vehicle;
    private RentalAgency rentalAgency;

    @BeforeEach
    void setUp() {
        customer = new Customer("Erling Haaland", "0534431116", "haaland@example.com");
        vehicle = new Motorcycle("Harley", 50.0, true, 500);
        rentalTransaction = new RentalTransaction(vehicle, customer, 25, 5.80, RentalTransaction.RentalStatus.ONGOING);
        rentalAgency = new RentalAgency();
    }

    @Test
    void testGetTotalCost() {
        double cost = rentalAgency.calculateRentalCost(vehicle, customer, 5);
        assertEquals(50.0 + 500 * 0.2 * 5, cost);
    }

    @Test
    void testGetStatus() {
        assertEquals(RentalTransaction.RentalStatus.ONGOING, rentalTransaction.getStatus());
    }

}
