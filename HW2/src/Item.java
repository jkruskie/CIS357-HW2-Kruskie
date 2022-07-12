import java.io.File;
import java.io.IOException;
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

    /**
     * Constructor for the Item class
     * @param itemCode String itemCode
     * @param itemName String itemName
     * @param itemPrice double itemPrice
     */
    public Item(String itemCode, String itemName, double itemPrice) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    /**
     * Get for itemCode
     * @return String itemCode
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * Set the itemCode
     * @param itemCode String itemCode
     */
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    /**
     * Get the item name
     * @return String itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Set the itemName to the given itemName
     * @param itemName String itemName
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Get the item price
     * @return Double itemPrice
     */
    public double getItemPrice() {
        return itemPrice;
    }

    /**
     * Set the itemPrice to the given price
     * @param itemPrice double itemPrice
     */
    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    /**
     * Get the item quantity
     * @return int itemQuantity
     */
    public int getItemQuantity() {
        return itemQuantity;
    }

    /**
     * Set the item quantity
     * @param itemQuantity int itemQuantity
     */
    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    /**
     * Take a ArrayList of Items and a filename and save the items to the file as a CSV
     * @param items ArrayList of Items
     * @param filename String filename
     */
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
