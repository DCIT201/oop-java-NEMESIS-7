package com.Vehicle.rental.services;

import com.Vehicle.rental.models.Customer;

public interface Rentable {
    double rent(Customer customer, int days);
    void returnVehicle();
    boolean isAvailableForRent();
}
