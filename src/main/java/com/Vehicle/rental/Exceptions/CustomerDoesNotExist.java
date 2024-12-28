package com.Vehicle.rental.Exceptions;

public class CustomerDoesNotExist extends RuntimeException{
    public CustomerDoesNotExist(String message){
        super(message);
    }
}
