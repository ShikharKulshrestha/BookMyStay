abstract class Room {
    private String roomType;
    private int bedCount;
    private double pricePerNight;
   
    protected boolean isAvailable;

    public Room(String roomType, int bedCount, double pricePerNight, boolean isAvailable) {
        this.roomType = roomType;
        this.bedCount = bedCount;
        this.pricePerNight = pricePerNight;
        this.isAvailable = isAvailable;
    }

    
    public abstract void displayRoomDetails();

   
    public String getRoomType() { return roomType; }
    public int getBedCount() { return bedCount; }
    public double getPricePerNight() { return pricePerNight; }
    public boolean isAvailable() { return isAvailable; }
}

class SingleRoom extends Room {
    public SingleRoom(boolean isAvailable) {
        super("Single Room", 1, 100.0, isAvailable);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println(getRoomType() + " | Beds: " + getBedCount() +
                " | Price: $" + getPricePerNight() +
                " | Available: " + isAvailable());
    }
}

class DoubleRoom extends Room {
    public DoubleRoom(boolean isAvailable) {
        super("Double Room", 2, 180.0, isAvailable);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println(getRoomType() + " | Beds: " + getBedCount() +
                " | Price: $" + getPricePerNight() +
                " | Available: " + isAvailable());
    }
}

class SuiteRoom extends Room {
    public SuiteRoom(boolean isAvailable) {
        super("Suite Room", 2, 350.0, isAvailable);
    }

    @Override
    public void displayRoomDetails() {
        System.out.println(getRoomType() + " | Beds: " + getBedCount() +
                " | Price: $" + getPricePerNight() +
                " | Available: " + isAvailable());
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Book My Stay App: Room Inventory ---");

        
        Room room1 = new SingleRoom(true);  // Available
        Room room2 = new DoubleRoom(true);  // Available
        Room room3 = new SuiteRoom(false);  // Booked

        
        room1.displayRoomDetails();
        room2.displayRoomDetails();
        room3.displayRoomDetails();

        System.out.println("----------------------------------------");
    }
}
