package com.Vehicle.rental.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        vehicle = new Car("Honda", 150,true,true);
    }

    @Test
    void getRatings() {
        vehicle.setRatings(List.of(
                1,
                2,
                5
        ));
        assertEquals(((double) (1 + 2 + 5) / 3), vehicle.getRating());

    }

    @Test
    void setRatings() {
        vehicle.setRatings(List.of(
                1,
                2,
                5
        ));
        assertTrue(vehicle.getRatings().contains(1));
    }

    @Test
    void addRating() {
        vehicle.addRating(4);
        assertTrue(vehicle.getRatings().contains(4));
    }

    @Test
    void getNumberOfRatings() {
        vehicle.setRatings(List.of(
                1,
                2,
                2
        ));
        assertEquals(3, vehicle.getNumberOfRatings());
    }

    @Test
    void addInvalidRating() {
        assertThrows(IllegalArgumentException.class, () -> vehicle.addRating(10));
    }
}