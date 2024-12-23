package com.Vehicle.rental.models;

import com.Vehicle.rental.Exceptions.VehicleAlreadyExists;
import com.Vehicle.rental.Exceptions.VehicleNotAvailable;
import com.Vehicle.rental.Exceptions.VehicleDoesNotExist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RentalAgencyTest {

    private RentalAgency rentalAgency;
    private Vehicle motorcycle;
    private Customer customer;

    @BeforeEach
    void setUp() {
        rentalAgency = new RentalAgency();
        motorcycle = new Motorcycle(UUID.randomUUID(), "Harley", 50.0, true, 500);
        customer = new Customer("John Doe", "0534431116", "john@example.com");
    }

    @Test
    void testAddToFleet() {
        rentalAgency.addToFleet(motorcycle);
        assertTrue(rentalAgency.getFleet().contains(motorcycle));
    }

    @Test
    void testAddToFleet_ThrowsVehicleAlreadyExists() {
        rentalAgency.addToFleet(motorcycle);
        assertThrows(VehicleAlreadyExists.class, () -> rentalAgency.addToFleet(motorcycle));
    }

    @Test
    void testRentVehicle() {
        rentalAgency.addToFleet(motorcycle);
        RentalTransaction transaction = rentalAgency.rentVehicle(motorcycle.getVehicleId(), customer, 5);
        assertNotNull(transaction);
        assertEquals(RentalTransaction.RentalStatus.ONGOING, transaction.getStatus());
    }

    @Test
    void testRentVehicle_ThrowsVehicleDoesNotExist() {
        assertThrows(VehicleDoesNotExist.class, () -> rentalAgency.rentVehicle(UUID.randomUUID(), customer, 5));
    }

    @Test
    void testReturnVehicle() {
        rentalAgency.addToFleet(motorcycle);
        RentalTransaction transaction = rentalAgency.rentVehicle(motorcycle.getVehicleId(), customer, 5);
        rentalAgency.returnVehicle(transaction.getTransactionId());
        assertEquals(RentalTransaction.RentalStatus.RETURNED, transaction.getStatus());
    }
}
