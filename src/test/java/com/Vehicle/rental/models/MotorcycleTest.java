package com.Vehicle.rental.models;

import com.Vehicle.rental.Exceptions.VehicleNotAvailable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MotorcycleTest {

    private Motorcycle motorcycle;
    private UUID vehicleId;

    @BeforeEach
    void setUp() {
        vehicleId = UUID.randomUUID();
        motorcycle = new Motorcycle(vehicleId, "Harley", 50.0, true, 500);
    }

    @Test
    void testCalculateRentalCost() {
        double rentalCost = motorcycle.calculateRentalCost(5);
        assertEquals(50.0 + 500 * 0.2 * 5, rentalCost);
    }

    @Test
    void testRent_WhenAvailable() {
        Customer customer = new Customer("John Doe", "0534431116", "john@example.com");
        double cost = motorcycle.rent(customer, 5);
        assertEquals(50.0 + 500 * 0.2 * 5, cost);
        assertFalse(motorcycle.isAvailable()); // Availability should be set to false after rent
    }

    @Test
    void testRent_WhenNotAvailable() {
        motorcycle.setAvailable(false);
        Customer customer = new Customer("John Doe", "0534431116", "john@example.com");
        assertThrows(VehicleNotAvailable.class, () -> motorcycle.rent(customer, 5));
    }

    @Test
    void testReturnVehicle() {
        motorcycle.setAvailable(false);
        motorcycle.returnVehicle();
        assertTrue(motorcycle.isAvailable());
    }
}
