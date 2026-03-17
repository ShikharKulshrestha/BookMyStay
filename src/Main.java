import java.io.*;
import java.util.*;

class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    public void setInventory(String type, int count) {
        inventory.put(type, count);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void printInventory() {
        System.out.println("Current Inventory:");
        System.out.println("Single: " + inventory.get("Single"));
        System.out.println("Double: " + inventory.get("Double"));
        System.out.println("Suite: " + inventory.get("Suite"));
    }
}

class FilePersistenceService {

    public void saveInventory(RoomInventory inventory, String filePath) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (Map.Entry<String, Integer> entry : inventory.getInventory().entrySet()) {
                writer.write(entry.getKey() + "-" + entry.getValue());
                writer.newLine();
            }
            writer.close();
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving inventory.");
        }
    }

    public void loadInventory(RoomInventory inventory, String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("-");
                String type = parts[0];
                int count = Integer.parseInt(parts[1]);
                inventory.setInventory(type, count);
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("No valid inventory data found. Starting fresh.");
        }
    }
}

public class Main {

    public static void main(String[] args) {

        System.out.println("System Recovery");

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService persistenceService = new FilePersistenceService();

        String filePath = "inventory.txt";

        persistenceService.loadInventory(inventory, filePath);

        inventory.printInventory();

        persistenceService.saveInventory(inventory, filePath);
    }
}