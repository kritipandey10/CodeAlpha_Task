import java.io.*;
import java.util.*;

class Room {
    String category;
    int roomNumber;
    boolean isBooked;

    Room(int roomNumber, String category) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isBooked = false;
    }
}

class Reservation {
    String guestName;
    int roomNumber;
    String category;
    boolean isPaid;

    Reservation(String guestName, int roomNumber, String category) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
        this.category = category;
        this.isPaid = false;
    }

    void displayDetails() {
        System.out.println("Guest: " + guestName);
        System.out.println("Room: " + roomNumber + " (" + category + ")");
        System.out.println("Payment Status: " + (isPaid ? "Paid" : "Pending"));
        System.out.println("---------------------------");
    }
}

public class HotelReservationSystem {
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Reservation> reservations = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeRooms();
        loadReservations();

        System.out.println("Welcome to the Hotel Reservation System!");
        boolean running = true;

        while (running) {
            System.out.println("\n1. Search Rooms\n2. Book Room\n3. Cancel Reservation\n4. View Reservations\n5. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> searchRooms();
                case 2 -> bookRoom();
                case 3 -> cancelReservation();
                case 4 -> viewReservations();
                case 5 -> {
                    saveReservations();
                    running = false;
                    System.out.println("Thanks for visiting!");
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void initializeRooms() {
        rooms.add(new Room(101, "Standard"));
        rooms.add(new Room(102, "Deluxe"));
        rooms.add(new Room(103, "Suite"));
        rooms.add(new Room(104, "Standard"));
        rooms.add(new Room(105, "Deluxe"));
    }

    static void searchRooms() {
        System.out.println("🔍 Available Rooms:");
        for (Room room : rooms) {
            if (!room.isBooked) {
                System.out.println("Room " + room.roomNumber + " - " + room.category);
            }
        }
    }

    static void bookRoom() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        searchRooms();
        System.out.print("Enter room number to book: ");
        int roomNum = scanner.nextInt();
        scanner.nextLine();

        for (Room room : rooms) {
            if (room.roomNumber == roomNum && !room.isBooked) {
                room.isBooked = true;
                Reservation res = new Reservation(name, roomNum, room.category);
                simulatePayment(res);
                reservations.add(res);
                System.out.println("✅ Room booked successfully!");
                return;
            }
        }
        System.out.println("❌ Room unavailable or invalid.");
    }

    static void cancelReservation() {
        System.out.print("Enter your name to cancel: ");
        String name = scanner.nextLine();
        boolean found = false;

        Iterator<Reservation> iterator = reservations.iterator();
        while (iterator.hasNext()) {
            Reservation res = iterator.next();
            if (res.guestName.equalsIgnoreCase(name)) {
                iterator.remove();
                for (Room room : rooms) {
                    if (room.roomNumber == res.roomNumber) {
                        room.isBooked = false;
                    }
                }
                System.out.println(" Reservation cancelled.");
                found = true;
                break;
            }
        }
        if (!found) System.out.println("No reservation found.");
    }

    static void viewReservations() {
        if (reservations.isEmpty()) {
            System.out.println(" No reservations yet.");
            return;
        }
        System.out.println("Current Reservations:");
        for (Reservation res : reservations) {
            res.displayDetails();
        }
    }

    static void simulatePayment(Reservation res) {
        System.out.print("Simulate payment? (yes/no): ");
        String pay = scanner.nextLine();
        res.isPaid = pay.equalsIgnoreCase("yes");
    }

    static void saveReservations() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("reservations.dat"))) {
            oos.writeObject(reservations);
        } catch (IOException e) {
            System.out.println("Error saving reservations.");
        }
    }

    @SuppressWarnings("unchecked")
    static void loadReservations() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("reservations.dat"))) {
            reservations = (ArrayList<Reservation>) ois.readObject();
            for (Reservation res : reservations) {
                for (Room room : rooms) {
                    if (room.roomNumber == res.roomNumber) {
                        room.isBooked = true;
                    }
                }
            }
        } catch (Exception e) {
            reservations = new ArrayList<>();
        }
    }
}