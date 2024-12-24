package com.Vehicle.rental.models;

import com.Vehicle.rental.Exceptions.VehicleAlreadyExists;
import com.Vehicle.rental.Exceptions.VehicleNotAvailable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
class CarTest {
    private Car car;
    private UUID vehicleId;
    private Customer customer;

    @BeforeEach
    void setUp() {
        vehicleId = UUID.randomUUID();
        car = new Car("Honda", 150,true,true);
        customer = new Customer("Jack Sparrow", "0534431116", "jacksparrow@gmail.com");
    }

    @Test
    void isHasClimateControl() {
        assertTrue(car.isHasClimateControl());
    }

    @Test
    void setHasClimateControl() {
        car.setHasClimateControl(false);
        assertFalse(car.isHasClimateControl());
    }

    @Test
    void calculateRentalCost() {
        double cost = car.getBaseRentalRate() * 5;
        assertEquals(cost + 15 * 5 , car.calculateRentalCost(5));
    }

    @Test
    void isAvailableForRental() {
        car.isAvailableForRental();
        assertTrue(car.isAvailableForRental());
    }

    @Test
    void testToString() {
        String toString = car.toString();
        assertTrue(toString.contains("Honda"));
    }

    @Test
    void rent() {
        double cost = car.getBaseRentalRate() * 5;
        assertEquals(cost + 15 * 5, car.rent(customer, 5));
    }

    @Test
    void isAvailableForRent() {
        assertTrue(car.isAvailableForRent());
        car.setAvailable(false);
        assertFalse(car.isAvailableForRent());
    }
    @Test
    void testRent_WhenAvailable() {
        double cost = car.rent(customer, 5);
        double price = car.getBaseRentalRate() * 5;
        assertEquals(price + 15 * 5, cost);
        assertFalse(car.isAvailable()); // Availability should be set to false after rent
    }
    @Test
    void testRent_WhenNotAvailable() {
        car.setAvailable(false);
        assertThrows(VehicleNotAvailable.class, () -> car.rent(customer, 5));

    }
    @Test
    void testReturnVehicle() {
        car.setAvailable(false);
        car.returnVehicle();
        assertTrue(car.isAvailable());
    }
    @Test
    void testReturnVehicle_WhenNotAvailable() {
        assertThrows(VehicleAlreadyExists.class, () -> car.returnVehicle());
    }
    @Test
    void testRateWithoutClimateControl() {
        car.setHasClimateControl(false);
        double cost = car.getBaseRentalRate() * 5;
        assertEquals(cost, car.calculateRentalCost(5));
    }
}