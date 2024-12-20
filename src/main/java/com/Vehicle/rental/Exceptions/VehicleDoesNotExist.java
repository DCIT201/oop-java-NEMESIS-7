package com.Vehicle.rental.Exceptions;

public class VehicleDoesNotExist extends RuntimeException {
    public VehicleDoesNotExist(String message) {
        super(message);
    }
}
