import java.io.*;
import java.util.*;

class Room {
    String type;
    int roomNumber;
    boolean isAvailable;

    Room(String type, int roomNumber) {
        this.type = type;
        this.roomNumber = roomNumber;
        this.isAvailable = true;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + type + ") - " + (isAvailable ? "Available" : "Booked");
    }
}

class Booking {
    String customerName;
    String roomType;
    int roomNumber;
    double price;

    Booking(String customerName, String roomType, int roomNumber, double price) {
        this.customerName = customerName;
        this.roomType = roomType;
        this.roomNumber = roomNumber;
        this.price = price;
    }

    @Override
    public String toString() {
        return customerName + " booked Room " + roomNumber + " (" + roomType + ") - ₹" + price;
    }
}

public class HotelBookingSystem {

    static List<Room> rooms = new ArrayList<>();
    static List<Booking> bookings = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "bookings.txt";

    public static void main(String[] args) {
        initializeRooms();
        loadBookingsFromFile();

        while (true) {
            System.out.println("\n====== Hotel Booking System ======");
            System.out.println("1. Show Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Bookings");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> showAvailableRooms();
                case 2 -> bookRoom();
                case 3 -> cancelBooking();
                case 4 -> viewBookings();
                case 5 -> {
                    saveBookingsToFile();
                    System.out.println("Exiting system...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void initializeRooms() {
        // Adding sample rooms
        for (int i = 1; i <= 5; i++) rooms.add(new Room("Standard", 100 + i));
        for (int i = 1; i <= 3; i++) rooms.add(new Room("Deluxe", 200 + i));
        for (int i = 1; i <= 2; i++) rooms.add(new Room("Suite", 300 + i));
    }

    static void showAvailableRooms() {
        System.out.println("\n--- Available Rooms ---");
        for (Room room : rooms) {
            if (room.isAvailable) System.out.println(room);
        }
    }

    static void bookRoom() {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.print("Enter room type (Standard/Deluxe/Suite): ");
        String type = sc.nextLine();

        Room selectedRoom = null;
        for (Room room : rooms) {
            if (room.type.equalsIgnoreCase(type) && room.isAvailable) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("No available rooms in this category.");
            return;
        }

        // Simulate payment
        double price = getPrice(selectedRoom.type);
        System.out.println("Room found: " + selectedRoom.roomNumber + " - ₹" + price);
        System.out.print("Confirm payment and book? (yes/no): ");
        if (sc.nextLine().equalsIgnoreCase("yes")) {
            selectedRoom.isAvailable = false;
            Booking booking = new Booking(name, selectedRoom.type, selectedRoom.roomNumber, price);
            bookings.add(booking);
            System.out.println("Booking successful!");
        } else {
            System.out.println("Booking cancelled.");
        }
    }

    static void cancelBooking() {
        System.out.print("Enter your name to cancel booking: ");
        String name = sc.nextLine();

        Booking toCancel = null;
        for (Booking booking : bookings) {
            if (booking.customerName.equalsIgnoreCase(name)) {
                toCancel = booking;
                break;
            }
        }

        if (toCancel != null) {
            bookings.remove(toCancel);
            for (Room room : rooms) {
                if (room.roomNumber == toCancel.roomNumber) {
                    room.isAvailable = true;
                    break;
                }
            }
            System.out.println("Booking cancelled successfully.");
        } else {
            System.out.println("No booking found under your name.");
        }
    }

    static void viewBookings() {
        System.out.println("\n--- All Bookings ---");
        if (bookings.isEmpty()) {
            System.out.println("No bookings made.");
        } else {
            for (Booking b : bookings) {
                System.out.println(b);
            }
        }
    }

    static double getPrice(String type) {
        return switch (type.toLowerCase()) {
            case "standard" -> 2000;
            case "deluxe" -> 3500;
            case "suite" -> 5000;
            default -> 0;
        };
    }

    static void saveBookingsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Booking b : bookings) {
                writer.println(b.customerName + "," + b.roomType + "," + b.roomNumber + "," + b.price);
            }
        } catch (IOException e) {
            System.out.println("Error saving bookings.");
        }
    }

    static void loadBookingsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Booking booking = new Booking(data[0], data[1], Integer.parseInt(data[2]), Double.parseDouble(data[3]));
                bookings.add(booking);
                for (Room room : rooms) {
                    if (room.roomNumber == booking.roomNumber) {
                        room.isAvailable = false;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("No previous bookings found.");
        }
    }
}

