package com.Vehicle.rental.models;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class RentalTransaction {
    private final UUID transactionId;
    private final Vehicle rentedVehicle;
    private final Customer customer;
    private final LocalDateTime rentalDate;
    private LocalDateTime dateToReturn;
    private long duration;
    private double totalPrice;
    private RentalStatus status;

    public RentalTransaction(Vehicle rentedVehicle, Customer customer, long duration, double totalPrice, RentalStatus status) {
        this.transactionId = UUID.randomUUID();
        this.rentedVehicle = rentedVehicle;
        this.customer = customer;
        this.rentalDate = LocalDateTime.now();
        this.dateToReturn = rentalDate.plusDays(duration);
        this.duration = duration;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public LocalDateTime dateRented() {
        return dateToReturn;
    }

    public void setLocalDateTimeRented(LocalDateTime dateToReturn) {
        this.dateToReturn = dateToReturn;
    }

    public Vehicle getRentedVehicle() {
        return rentedVehicle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public long getDuration() {
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
                ", customer=" + customer.getCustomerId() +
                ", rentalDate=" + rentalDate +
                ", dateToReturn=" + dateToReturn +
                ", duration=" + duration +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                '}';
    }
    public enum RentalStatus {
        ONGOING, RETURNED, CANCELLED
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RentalTransaction that = (RentalTransaction) o;
        return duration == that.duration && Double.compare(totalPrice, that.totalPrice) == 0 && Objects.equals(transactionId, that.transactionId) && Objects.equals(rentedVehicle, that.rentedVehicle) && Objects.equals(customer, that.customer) && Objects.equals(rentalDate, that.rentalDate) && Objects.equals(dateToReturn, that.dateToReturn) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, rentedVehicle, customer, rentalDate, dateToReturn, duration, totalPrice, status);
    }
}
