package com.Vehicle.rental.services;

import com.Vehicle.rental.models.Vehicle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface VehicleService {
    void addVehicle(Vehicle vehicle);

    Vehicle getVehicleById(UUID vehicleId);

    List<Vehicle> getAllVehicles(Class <? extends Vehicle> vehicle);

    boolean rentVehicle(UUID vehicleId, int days);

    double calculateCost(UUID vehicleId, int days);

    List<Vehicle> getAvailableVehicles();

    List<Vehicle> getAllVehicles();
}
