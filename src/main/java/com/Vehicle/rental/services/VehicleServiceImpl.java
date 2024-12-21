package com.Vehicle.rental.services;

import com.Vehicle.rental.models.Vehicle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VehicleServiceImpl implements VehicleService {

    private final Map<UUID, Vehicle> vehicleDB = new HashMap<>();

    @Override
    public void addVehicle(Vehicle vehicle) {
        if (vehicleDB.containsKey(vehicle.getVehicleId())){
            throw new IllegalArgumentException("Vehicle already exists");
        }
        vehicleDB.put(vehicle.getVehicleId(), vehicle);
    }

    @Override
    public Vehicle getVehicleById(UUID vehicleId) {
        return vehicleDB.get(vehicleId);
    }

    @Override
    public List<Vehicle> getAllVehicles(Class<? extends Vehicle> type) {
        return vehicleDB.values()
                .stream()
                .filter(vehicle -> type.isInstance(vehicle) && vehicle.isAvailable())
                .toList();
    }

    @Override
    public boolean rentVehicle(UUID vehicleId, int days) {
        Vehicle vehicle = vehicleDB.get(vehicleId);
        if(vehicle != null && vehicle.isAvailable()) {
            System.out.println(vehicle.getModel() + " has been rented for " + days + " days");
            vehicle.setAvailable(false);
        }
        throw new IllegalArgumentException("The vehicle you're looking for is currently unavailable");

    }

    @Override
    public double calculateCost(UUID vehicleId, int days) {
        Vehicle vehicle = vehicleDB.get(vehicleId);
        if(vehicle != null) {
            return vehicle.calculateRentalCost(days);
        }
        throw new IllegalArgumentException("The vehicle you're looking for is currently unavailable");
    }

    @Override
    public List<Vehicle> getAvailableVehicles() {
        return vehicleDB
                .values()
                .stream()
                .toList();
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return List.copyOf(vehicleDB.values());
    }
}
