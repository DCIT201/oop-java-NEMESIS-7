package com.Vehicle.rental.Exceptions;

public class NoVehicles extends RuntimeException{
    public NoVehicles(String message){
        super(message);
    }
}
