package com.Vehicle.rental.Exceptions;

public class RecordDoesNotExist extends RuntimeException{
    public RecordDoesNotExist(String message){
        super(message);
    }
}
