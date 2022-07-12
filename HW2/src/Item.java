import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

// Homework 2: Sales Register Program
// Course: CIS357
// Due date: 07/12/2022
// Name: Justin Kruskie
// GitHub: https://github.com/jkruskie/CIS357-HW2-Kruskie
// Instructor: Il-Hyung Cho
// Program description: Emulate cash register at a Grocery Store

public class Item {
    // Create variables for the itemCode, itemName, and itemPrice
    private String itemCode;
    private String itemName;
    private double itemPrice;
    private int itemQuantity;

    // Create a constructor to accept the itemCode, itemName, and itemPrice
    public Item(String itemCode, String itemName, double itemPrice) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    // Generate getters and setters for the itemCode, itemName, itemPrice, and itemQuantity
    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    // Take a ArrayList of Items and a filename and save the items to the file as a CSV
    public static void saveItems(List<Item> items, String filename) {
        // Delete the file if it exists
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }

        // Create a new file called filename
        try {
            file = new File(filename);
            // Create a new file writer
            java.io.FileWriter fw = new java.io.FileWriter(file.getAbsoluteFile());
            // Print all the items
            for (Item i : items) {
                // Write the itemCode, itemName, itemPrice, and itemQuantity to the file
                fw.write(i.getItemCode() + "," + i.getItemName() + "," + i.getItemPrice() + "\n");
            }
            // Close the file writer
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
