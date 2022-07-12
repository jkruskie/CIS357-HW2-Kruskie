import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

// Homework 1: Sales Register Program
// Course: CIS357
// Due date: 07/12/2022
// Name: Justin Kruskie
// GitHub: https://github.com/jkruskie/CIS357-HW2-Kruskie
// Instructor: Il-Hyung Cho
// Program description: Emulate cash register at a Grocery Store

/*
Program features:
Change the item code type to String: Y
Provide the input in CSV format. Ask the user to enter the input file name: Y
Implement exception handling for
    File input: Y
    Checking wrong input data type: Y
    Checking invalid data value: Y
    Tendered amount less than the total amount: N
Use ArrayList for the items data: P
Adding item data: N
Deleting item data: Y
Modifying item data: N
*/


public class HW2_Kruskie {

    // Public ArrayList to store the items in the register
    public static ArrayList<Item> items = new ArrayList<Item>();
    // Initialize a scanner
    private static Scanner scanner = new Scanner(System.in);
    // Create variable for csv file
    private static String csvFile;
    // Variables for data processing
    private static int selectedCode;
    private static int quantity = 0;

    /**
     * Main method
     * @param args
     */
    public static void main(String[] args) {

        // Print welcome message
        System.out.println("Welcome to POST system!");
        // Read the items from the CSV
        readItems();

        // Print beginning message
        System.out.print("Beginning a new sale (Y/N)? ");
        String newSale = scanner.next();

        while(newSale.toLowerCase().equals("y")) {
            // Clear transaction list
            Transaction.clearTransaction();
            if (newSale.toLowerCase().equals("y")) {
                do {
                    // Ask user for product codes
                    System.out.print("Enter product code: ");

                    // Get code from user
                    String code = scanner.next();

                    while(checkCode(code) == false) {
                        // Get new code from user
                        code = scanner.next();
                    }

                    // Check if code is -1 to end transaction, else continue
                    if(selectedCode == -1) {
                        continue;
                    }

                    // Get the item from the items ArrayList
                    Item item = items.get(selectedCode);

                    // Print the item name and ask for quantity
                    System.out.println("         Item name: " + item.getItemName());
                    System.out.print("Enter quantity:     ");

                    // Get quantity from user
                    String amount = scanner.next();
                    while(checkQuantity(amount) == false) {
                        // Get new quantity from user
                        amount = scanner.next();
                    }

                    // Add the item to the transaction
                    Transaction.addItem(item, quantity);

                    // Set the total to the item price * quantity
                    double total = item.getItemPrice() * quantity;
                    System.out.println("        item total: $   " + String.format("%.2f", total));


                } while (selectedCode != -1);

                // Selected code is -1, end transaction and print the required information
                completeSale();

                // Ask user if they want to continue a new sale
                System.out.print("Beginning a new sale (Y/N)? ");
                newSale = scanner.next();

            }
        } // User entered "N", end program
        // Ask user if they want to update any items
        System.out.println("===================");
        System.out.println("Total sales of the day is $ "+String.format("%.2f", Transaction.totalSales) + "\n");
        System.out.print("Do you want to update the items data? (A/D/M/Q): ");
        Boolean run = true;
        while(run) {
            String update = scanner.next();
            switch(update.toLowerCase()) {
                case "a":
                    // Add new item
                    System.out.print("Item code: ");
                    String code = scanner.next();
                    System.out.print("Item name: ");
                    scanner.nextLine();
                    String name = scanner.nextLine();
                    System.out.print("Item price: ");
                    Double price = scanner.nextDouble();
                    items.add(new Item(code.toUpperCase(), name, price));
                    System.out.println("Item added successfully!");
                    System.out.print("Do you want to update the items data? (A/D/M/Q): ");
//                    update = scanner.next();
                    break;
                case "d":
                    // Delete item
                    System.out.print("Item code: ");
                    code = scanner.next();
                    // Find item by code and delete it
                    Transaction.removeItem(code);
                    System.out.println("Item deleted successfully!");
                    System.out.print("Do you want to update the items data? (A/D/M/Q): ");
//                    update = scanner.next();
                    break;
                case "m":
                    // Modify item
                    System.out.print("Item code: ");
                    code = scanner.next();
                    System.out.print("Item name: ");
                    scanner.nextLine();
                    name = scanner.nextLine();
                    System.out.print("Item price: ");
                    price = scanner.nextDouble();
                    Transaction.modifyItem(code, name, price);
                    System.out.println("Item updated successfully!");
                    System.out.print("Do you want to update the items data? (A/D/M/Q): ");
//                    update = scanner.next();
                    break;
                case "q":
                    // Quit program
                    // List all items
                    printAllItemInfo();

                    // Save items back to csv file
                    Item.saveItems(items, csvFile);

                    run = false;
                    break;
                default:
                    System.out.print("Do you want to update the items data? (A/D/M/Q): ");
                    break;
            }
        }
        closeProgram();

    }

    /**
     * Read the items from the csv file and store them in the items ArrayList
     * @return void
     */
    public static void readItems() {
        try {

            // Ask user for the filename
            System.out.print("Input file: ");
            csvFile = scanner.next();

            // Check if the file exists
            while(Files.exists(Paths.get(csvFile)) == false) {
                // Get new filename from user
                System.out.print("File does not exist. Enter the filename: ");
                csvFile = scanner.next();
            }

            // Create a list of lines from the csv file
            List<String> lines = Files.readAllLines(Paths.get(csvFile));
            // Loop through the lines
            for (String line : lines) {
                // Split the line into an array of strings
                String[] item = line.split(",");
                // Create a new item and add it to the items ArrayList only if
                // the itemCode is a string, itemName is a string, and itemPrice is a double
                if (item[0] instanceof String && item[1] instanceof String && Double.valueOf(item[2]) instanceof Double) {
                    // Console log the item for debugging purposes
//                    System.out.println(item[0] + " " + item[1] + " " + item[2]);
                    items.add(new Item(item[0], item[1], Double.parseDouble(item[2])));
                } else {
                    // Log to console that the item data was not added and stop execution
                    // System.out.println("Item data was not added");
                    System.exit(1);
                }
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Checks if the given code is in the items ArrayList
     * @param code The code to check
     * @return boolean True if the code is in the items ArrayList, false otherwise
     */
    private static boolean checkCode(String code) {
        // Check if code is 0000 to display all item info
        if(code.equals("0000")) {
            // Print all item info
            printAllItemInfo();
            System.out.print("Enter product code: ");
            return false;
        }

        // Check if code is -1 to end transaction
        if(code.equals("-1")) {
            selectedCode = -1;
            return true;
        }

        // Check if item code exists in items ArrayList
        for (Item item : items) {
            if (item.getItemCode().equals(code.toUpperCase(Locale.ROOT))) {
                // If code exists return the item's index
                selectedCode = items.indexOf(item);
                return true;
            }
        }
        System.out.println("!!! Invalid code");
        System.out.print("Enter product code: ");
        return false;
    }

    /**
     * Check if the given quantity is valid
     * @param amount String containing the quantity entered by the user
     * @return boolean true if the quantity is valid, false if not
     */
    private static boolean checkQuantity(String amount) {
        try
        {
            int num = Integer.parseInt(amount);
            quantity = num;
            return true;
        }
        catch(NumberFormatException e)
        {
            System.out.println("!!! Invalid quantity");
            System.out.print("Enter quantity:     ");
            return false;
        }
    }

    /**
     * Close the program
     * @return void
     */
    private static void closeProgram() {
        System.out.println("Thanks for using POST System. Goodbye.");
    }

    /**
     * Print all item info
     * @return void
     */
    private static void printAllItemInfo() {
        System.out.format("%-15s%-15s%-15s\n", "item code", "item name", "unit price");
        for (Item item : items) {
            System.out.format("%-15s%-15s%.2f\n", item.getItemCode(), item.getItemName(), item.getItemPrice());
        }
    }

    /**
     * Print the end of the sale and print the transaction list
     * @return void
     */
    private static void completeSale() {
        // Print ending message
        System.out.println("----------------------------");
        System.out.println("Items list:");

        // Loop through the transaction list
        for (Item item : Transaction.getItems()) {
            // Print the item name information
            System.out.format("    %-3s%-13s$ %6.2f\n", item.getItemQuantity(), item.getItemName(), item.getItemPrice() * item.getItemQuantity());
        }

        Double total = Transaction.getTransactionTotal();
        Double totalTax = Transaction.getTransactionTotalWithTax();

        System.out.println("Subtotal            $   " + String.format("%.2f", total));
        System.out.println("Total with Tax (6%) $   " + String.format("%.2f", totalTax));

        System.out.print("Tendered Amount     $   ");

        double tender = scanner.nextDouble();

        while(tender < totalTax) {
            System.out.println("!!! Invalid amount");
            System.out.print("Tendered Amount     $   ");
            tender = scanner.nextDouble();
        }
        double change = tender - totalTax;

        System.out.println("Change              $   " + String.format("%.2f", change));

        System.out.println("----------------------------");
    }

}
