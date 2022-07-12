import java.util.ArrayList;
import java.util.Locale;

// Homework 1: Sales Register Program
// Course: CIS357
// Due date: 07/12/2022
// Name: Justin Kruskie
// GitHub: https://github.com/jkruskie/CIS357-HW2-Kruskie
// Instructor: Il-Hyung Cho
// Program description: Emulate cash register at a Grocery Store

public class Transaction {

    // ArrayList of items to keep track of the receipt
    public static ArrayList<Item> items = new ArrayList<Item>();
    // Double to keep track of total sales
    public static double totalSales = 0;

    // Method to accept item and add it to the items ArrayList
    // If the item exists already, increase the quantity using the quantity variable
    // If the item does not exist, add it to the items ArrayList
    public static void addItem(Item item, int quantity) {
        // Check if the item exists already
        for (Item i : items) {
            if (i.getItemCode().equals(item.getItemCode())) {
                // If it exists, increase the quantity
                i.setItemQuantity(i.getItemQuantity() + quantity);
                return;
            }
        }
        // If it does not exist, add it to the items ArrayList
        item.setItemQuantity(quantity);
        items.add(item);
    }

    // Getter for transaction list
    public static ArrayList<Item> getItems() {
        return items;
    }

    // Get the transaction total sales by adding up the item prices * the item quantity
    public static Double getTransactionTotal() {
        Double total = 0.0;
        for (Item i : items) {
            total += i.getItemPrice() * i.getItemQuantity();
        }
        return total;
    }

    // Get the transaction total after sales tax is added.
    // Sales tax is only added if the itemCode has "A" in it.
    public static Double getTransactionTotalWithTax() {
        Double total = 0.0;
        Double unTaxTotal = 0.0;
        for (Item i : items) {
            if (i.getItemCode().contains("A")) {
                total += (i.getItemPrice() * i.getItemQuantity());
            } else {
                unTaxTotal += (i.getItemPrice() * i.getItemQuantity());
            }
        }
        totalSales += (total + unTaxTotal);
        return ((total + (total * 0.06)) + unTaxTotal);
    }

    // Remove item based on itemCode
    public static void removeItem(String itemCode) {
        for (Item i : HW2_Kruskie.items) {
            if (i.getItemCode().equals(itemCode.toUpperCase(Locale.ROOT))) {
                items.remove(i);
                return;
            }
        }
    }

    // Modify item based on itemCode, name, and price
    public static void modifyItem(String itemCode, String name, double price) {
        for (Item i : HW2_Kruskie.items) {
            if (i.getItemCode().equals(itemCode.toUpperCase(Locale.ROOT))) {
                i.setItemName(name);
                i.setItemPrice(price);
                return;
            }
        }
    }

    // Empty the transaction list
    public static void clearTransaction() {
        items.clear();
    }
}
