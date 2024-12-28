package com.Vehicle.rental.models;

import com.Vehicle.rental.Exceptions.RecordDoesNotExist;
import com.Vehicle.rental.Exceptions.VehicleAlreadyExists;
import com.Vehicle.rental.Exceptions.VehicleDoesNotExist;
import com.Vehicle.rental.services.RentalAgency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RentalAgencyTest {

    private RentalAgency rentalAgency;
    private Vehicle motorcycle;
    private Customer customer;
    private Vehicle car;
    private Vehicle truck;

    @BeforeEach
    void setUp() {
        rentalAgency = new RentalAgency();
        motorcycle = new Motorcycle("Harley", 50.0, true, 500);
        car = new Car("Lambo,", 1500, true, true);
        truck = new Truck("CyberTruck", 5000, true, 1000);
        customer = new Customer("Shaq", "0534431116", "shaq@example.com");
    }

    @Test
    void testAddToFleet() {
        rentalAgency.addToFleet(motorcycle);
        assertTrue(rentalAgency.getFleet().contains(motorcycle));
        rentalAgency.addToFleet(car);
        assertTrue(rentalAgency.getFleet().contains(car));
        rentalAgency.addToFleet(truck);
        assertTrue(rentalAgency.getFleet().contains(truck));
    }

    @Test
    void testAddToFleet_ThrowsVehicleAlreadyExists() {
        rentalAgency.addToFleet(motorcycle);
        assertThrows(VehicleAlreadyExists.class, () -> rentalAgency.addToFleet(motorcycle));
    }

    @Test
    void testRentVehicle() {
        rentalAgency.addToFleet(motorcycle);
        RentalTransaction transaction = rentalAgency.rentVehicle(motorcycle.getVehicleId(), customer, 5);
        assertNotNull(transaction);
        assertEquals(RentalTransaction.RentalStatus.ONGOING, transaction.getStatus());
    }

    @Test
    void testRentVehicle_ThrowsVehicleDoesNotExist() {
        assertThrows(VehicleDoesNotExist.class, () -> rentalAgency.rentVehicle(UUID.randomUUID(), customer, 5));
    }

    @Test
    void testReturnVehicle() {
        rentalAgency.addToFleet(motorcycle);
        RentalTransaction transaction = rentalAgency.rentVehicle(motorcycle.getVehicleId(), customer, 5);
        rentalAgency.returnVehicle(transaction.getTransactionId());
        assertEquals(RentalTransaction.RentalStatus.RETURNED, transaction.getStatus());
    }

    @Test
    void calculateRentalCost_WithLoyaltyPoints(){
        rentalAgency.addToFleet(motorcycle);
        customer.setLoyaltyPoints(1);
        assertEquals(motorcycle.calculateRentalCost(5) / customer.getLoyaltyPoints() * 0.01, rentalAgency.calculateRentalCost(motorcycle,customer, 5));
    }
    @Test
    void calculateRentalCost_WithoutLoyaltyPoints(){
        rentalAgency.addToFleet(motorcycle);
        customer.setLoyaltyPoints(0);
        assertEquals(motorcycle.calculateRentalCost(5), rentalAgency.calculateRentalCost(motorcycle,customer, 5));
    }
    @Test
    void availableVehicles(){
        rentalAgency.addToFleet(motorcycle);
        motorcycle.setAvailable(false);
        assertFalse(rentalAgency.getAvailableVehicles().contains(motorcycle));
    }
    @Test
    void allVehicles(){
        rentalAgency.addToFleet(motorcycle);
        assertTrue(rentalAgency.getAvailableVehicles().contains(motorcycle));
    }

    @Test
    void cancelRental(){
        rentalAgency.addToFleet(motorcycle);
        RentalTransaction transaction = rentalAgency.rentVehicle(motorcycle.getVehicleId(), customer, 5);
        rentalAgency.cancelRental(transaction.getTransactionId());
        assertEquals(RentalTransaction.RentalStatus.CANCELLED, transaction.getStatus());
    }

    @Test
    void cancelRental_recordDoesNotExist(){
        assertThrows(RecordDoesNotExist.class, () -> rentalAgency.cancelRental(UUID.randomUUID()));
    }
    @Test
    void returnEmptyVehicle() {
        assertThrows(RecordDoesNotExist.class, () -> rentalAgency.returnVehicle(UUID.randomUUID()));
    }

    @Test
    void getAvailableVehicles() {
        rentalAgency.addToFleet(motorcycle);
        assertTrue(rentalAgency.getAvailableVehicles().contains(motorcycle));
    }
    @Test
    void getAvailableTypeVehicles() {
        rentalAgency.addToFleet(motorcycle);

        List<String> availableVehicles = rentalAgency.getAvailableTypeVehicles();

        String expectedMotorcycleDetails = "Model: " + motorcycle.getModel() + "\nBase Rental Rate: " + motorcycle.getBaseRentalRate();
        assertTrue(availableVehicles.contains(expectedMotorcycleDetails));
    }

    @Test
    void findCustomerByEmail() {
        rentalAgency.addCustomer(customer);
        Optional<Customer> foundCustomer = rentalAgency.findCustomerByEmail(customer.getEmail());
        assertTrue(foundCustomer.isPresent());
        assertEquals(customer, foundCustomer.get());
    }
    @Test
    void rateVehicle(){
        rentalAgency.addToFleet(motorcycle);
        rentalAgency.rateVehicle(motorcycle.getVehicleId(), 5);
        double rating = motorcycle.getRating();
        assertEquals(5, rating);
    }

    @Test
    void averageRating(){
        rentalAgency.addToFleet(motorcycle);
        motorcycle.setRatings(List.of(
                5,
                3,
                4
        ));
        assertEquals((double) (5 + 3 + 4) /3 , motorcycle.getRating());
    }

}
