// Simple Hotel Management System
// File: HotelManagementSystem.java

import java.util.ArrayList;
import java.util.Scanner;

class Room {
    private int roomNumber;
    private String type;
    private double price;
    private boolean isOccupied;
    private String guestName;

    public Room(int roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.isOccupied = false;
        this.guestName = "";
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public String getGuestName() {
        return guestName;
    }

    public void checkIn(String guestName) {
        this.guestName = guestName;
        this.isOccupied = true;
    }

    public void checkOut() {
        this.guestName = "";
        this.isOccupied = false;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + type + ") - $" + price + "/night - " +
                (isOccupied ? "Occupied by " + guestName : "Available");
    }
}

class Booking {
    private int bookingId;
    private String guestName;
    private int roomNumber;
    private int days;
    private double totalAmount;

    private static int nextBookingId = 1000;

    public Booking(String guestName, int roomNumber, int days, double pricePerNight) {
        this.bookingId = nextBookingId++;
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.days = days;
        this.totalAmount = days * pricePerNight;
    }

    @Override
    public String toString() {
        return "Booking #" + bookingId + ": " + guestName + " - Room " + roomNumber +
                " - " + days + " days - Total: $" + totalAmount;
    }
    
    public int getRoomNumber() {
        return roomNumber;
    }
    
    public String getGuestName() {
        return guestName;
    }
    
    public int getBookingId() {
        return bookingId;
    }
}

public class HotelManagementSystem {
    private static ArrayList<Room> rooms = new ArrayList<>();
    private static ArrayList<Booking> bookings = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeRooms();

        boolean running = true;
        while (running) {
            System.out.println("\n===== Hotel Management System =====");
            System.out.println("1. View All Rooms");
            System.out.println("2. Check In");
            System.out.println("3. Check Out");
            System.out.println("4. View All Bookings");
            System.out.println("5. Search Room");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    viewAllRooms();
                    break;
                case 2:
                    checkIn();
                    break;
                case 3:
                    checkOut();
                    break;
                case 4:
                    viewAllBookings();
                    break;
                case 5:
                    searchRoom();
                    break;
                case 6:
                    running = false;
                    System.out.println("Thank you for using the Hotel Management System!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        
        scanner.close();
    }

    private static void initializeRooms() {
        rooms.add(new Room(101, "Single", 50.0));
        rooms.add(new Room(102, "Single", 50.0));
        rooms.add(new Room(103, "Double", 80.0));
        rooms.add(new Room(104, "Double", 80.0));
        rooms.add(new Room(105, "Suite", 120.0));
    }

    private static void viewAllRooms() {
        System.out.println("\n===== All Rooms =====");
        for (Room room : rooms) {
            System.out.println(room);
        }
    }

    private static void checkIn() {
        System.out.println("\n===== Available Rooms =====");
        boolean hasAvailableRooms = false;
        
        for (Room room : rooms) {
            if (!room.isOccupied()) {
                System.out.println(room);
                hasAvailableRooms = true;
            }
        }
        
        if (!hasAvailableRooms) {
            System.out.println("No rooms available at the moment.");
            return;
        }
        
        System.out.print("\nEnter room number to check in: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine(); 
        
        Room selectedRoom = null;
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                selectedRoom = room;
                break;
            }
        }
        
        if (selectedRoom == null) {
            System.out.println("Invalid room number.");
            return;
        }
        
        if (selectedRoom.isOccupied()) {
            System.out.println("This room is already occupied.");
            return;
        }
        
        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine();
        
        System.out.print("Enter number of days: ");
        int days = scanner.nextInt();
        scanner.nextLine(); 
        
        selectedRoom.checkIn(guestName);
        bookings.add(new Booking(guestName, roomNumber, days, selectedRoom.getPrice()));
        
        System.out.println("Check-in successful!");
    }

    private static void checkOut() {
        System.out.print("Enter room number to check out: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine(); 
        
        Room selectedRoom = null;
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                selectedRoom = room;
                break;
            }
        }
        
        if (selectedRoom == null) {
            System.out.println("Invalid room number.");
            return;
        }
        
        if (!selectedRoom.isOccupied()) {
            System.out.println("This room is not occupied.");
            return;
        }
        
        Booking bookingToRemove = null;
        for (Booking booking : bookings) {
            if (booking.getRoomNumber() == roomNumber) {
                bookingToRemove = booking;
                break;
            }
        }
        
        if (bookingToRemove != null) {
            bookings.remove(bookingToRemove);
        }
        
        selectedRoom.checkOut();
        System.out.println("Check-out successful!");
    }

    private static void viewAllBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No current bookings.");
            return;
        }
        
        System.out.println("\n===== Current Bookings =====");
        for (Booking booking : bookings) {
            System.out.println(booking);
        }
    }
    
    private static void searchRoom() {
        System.out.println("Search room by:");
        System.out.println("1. Room Number");
        System.out.println("2. Room Type");
        System.out.print("Enter your choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); 
        
        switch (choice) {
            case 1:
                System.out.print("Enter room number: ");
                int roomNumber = scanner.nextInt();
                scanner.nextLine(); 
                
                boolean found = false;
                for (Room room : rooms) {
                    if (room.getRoomNumber() == roomNumber) {
                        System.out.println(room);
                        found = true;
                        break;
                    }
                }
                
                if (!found) {
                    System.out.println("Room not found.");
                }
                break;
                
            case 2:
                System.out.print("Enter room type (Single/Double/Suite): ");
                String roomType = scanner.nextLine();
                
                System.out.println("\n===== Search Results =====");
                boolean foundType = false;
                
                for (Room room : rooms) {
                    if (room.getType().equalsIgnoreCase(roomType)) {
                        System.out.println(room);
                        foundType = true;
                    }
                }
                
                if (!foundType) {
                    System.out.println("No rooms of type " + roomType + " found.");
                }
                break;
                
            default:
                System.out.println("Invalid choice.");
        }
    }
}