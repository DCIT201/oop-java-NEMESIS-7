package com.Vehicle.rental.Exceptions;

public class VehicleNotAvailable extends RuntimeException{
    public VehicleNotAvailable(String message) {
        super(message);
    }
}
