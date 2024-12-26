package com.Vehicle.rental.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer("Jack Frost", "0534431116", "elikemfenuksu@gmail.com");
    }

    @Test
    void addRating() {
        customer.addRating(2);
        assertTrue(customer.getRatings().contains(2));
    }

    @Test
    void getAverageRating() {
        customer.setRatings(List.of(
                1,
                2,
                5
        ));
        assertEquals((double)(1 + 2 + 5) / 3, customer.getAverageRating());
    }

    @Test
    void addInvalidRating() {
        assertThrows(IllegalArgumentException.class, () -> customer.addRating(-9));
    }

    @Test
    void getNumberOfRatings() {
        customer.setRatings(List.of(
                1,
                2,
                5
        ));
        assertEquals(3, customer.getNumberOfRatings());
    }
}