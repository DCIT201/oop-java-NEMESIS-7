package com.Vehicle.rental;

import com.Vehicle.rental.Exceptions.NoVehicles;
import com.Vehicle.rental.models.*;
import com.Vehicle.rental.services.RentalAgency;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        RentalAgency rentalAgency = new RentalAgency();
        Scanner input = new Scanner(System.in);

        boolean running = true;

        while (running) {
            System.out.println("\n--- Vehicle Rental Management System ---");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Add Customer");
            System.out.println("3. List All Vehicles");
            System.out.println("4. List All Customers");
            System.out.println("5. Rent a Vehicle");
            System.out.println("6. Return a Vehicle");
            System.out.println("7. Rate a Vehicle");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = input.nextInt();
            input.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addVehicle(rentalAgency, input);
                case 2 -> addCustomer(rentalAgency, input);
                case 3 -> System.out.println(getAll(rentalAgency));
                case 4 -> System.out.println(getCustomers(rentalAgency));
                case 5 -> rentVehicle(rentalAgency, input);
                case 6 -> returnVehicle(rentalAgency, input);
                case 7 -> rateVehicle(rentalAgency, input);
                case 8 -> {
                    running = false;
                    System.out.println("Exiting application. Goodbye!");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

        input.close();
    }

    private static void addVehicle(RentalAgency rentalAgency, Scanner input) {
        System.out.println("What kind of vehicle do you want to add? \n Please choose from Car, Motorcycle and Truck: ");
        String vehicleType = input.nextLine().trim().toLowerCase();

        System.out.println("\n--- Add Vehicle ---");

        System.out.print("Enter vehicle model: ");
        String model = input.nextLine();

        System.out.print("Enter base rental rate: ");
        double baseRate = input.nextDouble();

        System.out.print("Is the vehicle available now? (true/false): ");
        boolean isAvailable = input.nextBoolean();

        System.out.print("Does the vehicle have climate control? (true/false): ");
        boolean hasClimateControl = input.nextBoolean();
        input.nextLine();


        switch (vehicleType) {
            case "car":
                Vehicle car = new Car(model, baseRate, isAvailable, hasClimateControl);
                rentalAgency.addToFleet(car);
                System.out.println("Car added successfully.");
                break;
            case "motorcycle":
                System.out.println("What is the engine capacity?");
                int capacity = input.nextInt();
                Vehicle motorcycle = new Motorcycle(model, baseRate, isAvailable, capacity);
                rentalAgency.addToFleet(motorcycle);
                System.out.println("Motorcycle added successfully.");
                break;

            case "truck":
                System.out.println("What is the load capacity?");
                int loadCapacity = input.nextInt();
                Vehicle truck = new Truck(model, baseRate, isAvailable, loadCapacity);
                rentalAgency.addToFleet(truck);
                System.out.println("Truck added successfully.");
                break;

            default:
                System.out.println("Invalid vehicle type. Please try again.");
                break;
        }

       /* if (vehicleType.equalsIgnoreCase("Car")) {
            Vehicle car = new Car(model, baseRate, isAvailable, hasClimateControl);
            rentalAgency.addToFleet(car);
            System.out.println("Car added successfully.");

        }

        else if (vehicleType.equalsIgnoreCase("Motorcycle")) {
            System.out.println("What is the engine capacity?");
            int capacity = input.nextInt();
            Vehicle motorcycle = new Motorcycle(model, baseRate, isAvailable, capacity);
            rentalAgency.addToFleet(motorcycle);
            System.out.println("Motorcycle added successfully.");
        }

        else if (vehicleType.equalsIgnoreCase("Truck")) {
            System.out.println("What is the load capacity?");
            int capacity = input.nextInt();
            Vehicle truck = new Truck(model, baseRate, isAvailable, capacity);
            rentalAgency.addToFleet(truck);
            System.out.println("Truck added successfully.");
        }*/
    }

    private static void addCustomer(RentalAgency rentalAgency, Scanner input) {
        System.out.println("\n--- Add Customer ---");
        System.out.print("Enter customer name: ");
        String name = input.nextLine();
        System.out.print("Enter customer email: ");
        String email = input.nextLine();
        System.out.println("Enter customer phone: ");
        String phone = input.nextLine();

        Customer customer = new Customer(name, phone, email);
        rentalAgency.addCustomer(customer);
        System.out.println("Customer added successfully.");
    }

    private static void rentVehicle(RentalAgency rentalAgency, Scanner input) {
        System.out.println("\n--- Rent a Vehicle ---");
        System.out.println("Would you like to see a list of available vehicles? (yes/no) ");
        String answer = input.next().toLowerCase();
        input.nextLine();

        switch (answer) {
            case "yes":
                System.out.println(rentalAgency.getAvailableTypeVehicles());
                break;
            case "no":
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
                return;
        }

        System.out.println("Would you like to rent a vehicle? (yes/no) ");
        String rentAnswer = input.nextLine().toLowerCase();
        if (!rentAnswer.equals("yes")) {
            return;
        }

        System.out.print("Enter vehicle model: ");
        String model = input.nextLine();
        Optional<Vehicle> vehicle = rentalAgency.getVehicle(model);


        if (vehicle.isEmpty()) {
            handleInvalidInput("Vehicle with model " + model + " does not exist.", input);
            return;
        }
        Vehicle rentingCar = vehicle.get();


        System.out.print("Enter customer email: ");
        String email = input.nextLine();
        Optional<Customer> customer = rentalAgency.findCustomerByEmail(email);

        if (customer.isEmpty()) {
            handleInvalidInput("Customer with email " + email + " does not exist.", input);
            return;
        }
        Customer customerToRent = customer.get();

        System.out.print("Enter rental duration (days): ");
        int days = input.nextInt();
        input.nextLine();

        try {
            double cost = rentalAgency.calculateRentalCost(rentingCar, customerToRent, days);
            System.out.println("Vehicle rented successfully. Total cost: GHC" + cost);

            RentalTransaction transaction = rentalAgency.rentVehicle(rentingCar.getVehicleId(), customerToRent, days);
            System.out.println("This transaction ID you will use to return the vehicle: " + transaction.getTransactionId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    private static void handleInvalidInput(String message, Scanner input) {
        System.out.println(message);
        System.out.println("Would you like to exit or return to the main menu? (enter exit to leave)");
        String choice = input.nextLine().toLowerCase();
        if (choice.equalsIgnoreCase("exit")) {
            System.out.println("Exiting application. Goodbye!");
            System.exit(0);
        }
    }

    private static void returnVehicle(RentalAgency rentalAgency, Scanner input) {
        System.out.println("\n--- Return a Vehicle ---");
        System.out.print("Enter transaction ID: ");
        UUID transactionID = UUID.fromString(input.nextLine());


        try {
            rentalAgency.returnVehicle(transactionID);
            System.out.println("Vehicle returned successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void rateVehicle(RentalAgency rentalAgency, Scanner input) {
        System.out.println("\n--- Rate a Vehicle ---");
        System.out.print("Enter vehicle ID: ");
        UUID vehicleId = UUID.fromString(input.nextLine());
        System.out.print("Enter rating (1-5): ");
        int rating = input.nextInt();

        try {
            rentalAgency.rateVehicle(vehicleId, rating);
            System.out.println("Vehicle rated successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static List<Vehicle> getAll(RentalAgency rentalAgency) {
        return rentalAgency.getFleet();
    }
    private static List<Customer> getCustomers(RentalAgency rentalAgency) {
        return rentalAgency.getCustomers();
    }
}