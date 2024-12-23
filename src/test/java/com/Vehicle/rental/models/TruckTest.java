package com.Vehicle.rental.models;

import com.Vehicle.rental.Exceptions.VehicleNotAvailable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TruckTest {

    private Truck truck;
    private UUID vehicleId;

    @BeforeEach
    void setUp() {
        vehicleId = UUID.randomUUID();
        truck = new Truck(vehicleId, "Ford", 100.0, true, 10);
    }

    @Test
    void testCalculateRentalCost() {
        double rentalCost = truck.calculateRentalCost(5);
        assertEquals(100.0 + 10 * 5 * 0.5, rentalCost);
    }

    @Test
    void testRent_WhenAvailable() {
        Customer customer = new Customer("John Doe", "0534431116", "jane@example.com");
        double cost = truck.rent(customer, 5);
        assertEquals(100.0 + 10 * 5 * 0.5, cost);
        assertFalse(truck.isAvailable()); // Availability should be set to false after rent
    }

    @Test
    void testRent_WhenNotAvailable() {
        truck.setAvailable(false);
        Customer customer = new Customer("John Doe", "0534431116", "jane@example.com");
        assertThrows(VehicleNotAvailable.class, () -> truck.rent(customer, 5));
    }

    @Test
    void testReturnVehicle() {
        truck.setAvailable(false);
        truck.returnVehicle();
        assertTrue(truck.isAvailable());
    }
}
