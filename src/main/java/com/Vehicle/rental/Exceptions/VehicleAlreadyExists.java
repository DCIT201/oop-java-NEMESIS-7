package com.Vehicle.rental.Exceptions;

public class VehicleAlreadyExists extends RuntimeException {
    public VehicleAlreadyExists(String message) {
        super(message);
    }
}
