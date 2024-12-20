package com.Vehicle.rental.models;

import java.util.Date;
import java.util.UUID;

public class RentalTransaction {
    private final UUID transactionId;
    private final Vehicle rentedVehicle;
    private final Customer customer;
    private final Date rentalDate;
    private Date dateRented;
    private int duration;
    private double totalPrice;
    private RentalStatus status;

    public RentalTransaction(UUID transactionId, Vehicle rentedVehicle, Customer customer, Date rentalDate, Date dateRented, int duration, double totalPrice, RentalStatus status) {
        this.transactionId = transactionId;
        this.rentedVehicle = rentedVehicle;
        this.customer = customer;
        this.rentalDate = rentalDate;
        this.dateRented = dateRented;
        this.duration = duration;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Date getDateRented() {
        return dateRented;
    }

    public void setDateRented(Date dateRented) {
        this.dateRented = dateRented;
    }

    public Vehicle getRentedVehicle() {
        return rentedVehicle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "RentalTransaction{" +
                "transactionId=" + transactionId +
                ", rentedVehicle=" + rentedVehicle +
                ", customer=" + customer +
                ", rentalDate=" + rentalDate +
                ", dateRented=" + dateRented +
                ", duration=" + duration +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                '}';
    }

    public enum RentalStatus {
        ONGOING, COMPLETED, CANCELLED
    }
}
