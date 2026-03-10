import java.util.*;

class BookingRequest {
    String customerName;
    String roomType;

    public BookingRequest(String customerName, String roomType) {
        this.customerName = customerName;
        this.roomType = roomType;
    }
}

class InventoryService {

    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        inventory.put("STANDARD", 5);
        inventory.put("DELUXE", 3);
        inventory.put("SUITE", 2);
    }

    public synchronized boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public synchronized void decrementInventory(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void printInventory() {
        System.out.println("Current Inventory: " + inventory);
    }
}

class BookingService {

    private Queue<BookingRequest> requestQueue = new LinkedList<>();


    private Map<String, Set<String>> allocatedRooms = new HashMap<>();


    private Set<String> usedRoomIds = new HashSet<>();

    private InventoryService inventoryService;

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void addBookingRequest(BookingRequest request) {
        requestQueue.offer(request);
    }

    private String generateRoomId(String roomType) {
        String roomId;

        do {
            roomId = roomType.substring(0, 2).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 5);
        } while (usedRoomIds.contains(roomId));

        return roomId;
    }

    public void processBookings() {

        while (!requestQueue.isEmpty()) {

            BookingRequest request = requestQueue.poll();

            System.out.println("\nProcessing booking for: " + request.customerName);

            synchronized (this) {

                if (!inventoryService.isAvailable(request.roomType)) {
                    System.out.println("No rooms available for type: " + request.roomType);
                    continue;
                }


                String roomId = generateRoomId(request.roomType);


                usedRoomIds.add(roomId);


                allocatedRooms
                        .computeIfAbsent(request.roomType, k -> new HashSet<>())
                        .add(roomId);


                inventoryService.decrementInventory(request.roomType);

                System.out.println("Reservation confirmed!");
                System.out.println("Customer: " + request.customerName);
                System.out.println("Room Type: " + request.roomType);
                System.out.println("Assigned Room ID: " + roomId);
            }
        }
    }

    public void printAllocatedRooms() {
        System.out.println("\nAllocated Rooms: " + allocatedRooms);
    }
}

public class Main {

    public static void main(String[] args) {

        InventoryService inventoryService = new InventoryService();
        BookingService bookingService = new BookingService(inventoryService);


        bookingService.addBookingRequest(new BookingRequest("Alice", "STANDARD"));
        bookingService.addBookingRequest(new BookingRequest("Bob", "DELUXE"));
        bookingService.addBookingRequest(new BookingRequest("Charlie", "STANDARD"));
        bookingService.addBookingRequest(new BookingRequest("David", "SUITE"));


        bookingService.processBookings();


        bookingService.printAllocatedRooms();
        inventoryService.printInventory();
    }
}