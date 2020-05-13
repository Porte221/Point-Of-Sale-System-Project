package com.assignment.seis602.register;

import com.assignment.seis602.cashier.Cashier;
import com.assignment.seis602.cashier.CashierAuthenticator;
import com.assignment.seis602.cashier.IPoSSecurity;
import com.assignment.seis602.interfaces.IPoSReports;
import com.assignment.seis602.inventory.Inventory;
import com.assignment.seis602.item.InventoryItem;
import com.assignment.seis602.item.SaleItem;
import com.assignment.seis602.logging.ILogger;
import com.assignment.seis602.logging.RegisterLog;
import com.assignment.seis602.sale.Sale;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Scanner;

/**
 * @Author Ryan Poorman
 * @Description PoSRegisterUI is used to attempt to abstract much of the console generated messages and interactions away from the core processing
 * of the register itself
 */
public class PoSRegisterUI implements IPoSReports, IPoSSecurity {

    Inventory inventory = null;
    Cashier cashier = null;
    Integer registerId = null;
    Sale saleItem = null;
    RegisterLog logger = null;


    public String displayUIOptions() throws IOException {

        ILogger.logToConsole("\r\n\r\nSelect one of the following options: (Enter associated numeric value)");
        ILogger.logToConsole("1. New Sale");
        ILogger.logToConsole("2. Return Sale");
        ILogger.logToConsole("3. Sign Out");
        ILogger.logToConsole("4. Shutdown");
        ILogger.logToConsole("5. Print Register Report");
        ILogger.logToConsole("6. Print Detailed Register Report");
        ILogger.logToConsole("7. Print Inventory Report");

        Scanner in = new Scanner(System.in);
        return in.nextLine();
    }


    public String displayUIOrderOptions() throws IOException {
        ILogger.logToConsole("\n" +
                "\n1. Add Item");
        ILogger.logToConsole("2. Remove Item");
        ILogger.logToConsole("3. Cancel Sale");
        ILogger.logToConsole("4. Checkout");
        ILogger.logToConsole("5. View Current Sale");


        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        return reader.readLine();
    }


    public void printReceipt() {
        ILogger.logToConsole("\n" +
                "\n--Point of Sale Receipt--");

        printItemsInCurrentSale();
        ILogger.logToConsole("Total Price: " + saleItem.getSaleAmount());
    }

    public void printItemsInCurrentSale() {
        Map<String, SaleItem> items = saleItem.getSaleItems();
        for (Map.Entry<String, SaleItem> mapElement : items.entrySet()) {
            String itemName = (String) mapElement.getKey();
            ILogger.logToConsole(mapElement.getValue().getItemCount() + " " + itemName + " || Price: $" + mapElement.getValue().getItem().getUnitPrice() * mapElement.getValue().getItemCount());
        }
    }

    public void printAvailableItems() {
        Map<String, InventoryItem> items = inventory.getAvailableInventoryItems();
        InventoryItem i = null;
        for (Map.Entry<String, InventoryItem> mapElement : items.entrySet()) {
            String itemName = (String) mapElement.getKey();
            ILogger.logToConsole("Product Name: " + itemName + " || Price: $" + mapElement.getValue().getItem().getUnitPrice());
        }
        ILogger.logToConsole("Running Price: " + saleItem.getSaleAmount());
    }


    public void generateReport() {
        logger.generateReport();
    }

    public void generateDetailedReport(String employeeName) {
        logger.generateDetailedReport(employeeName);
    }

    public void generateInventoryReport() {
        inventory.printDetailedInventory();
    }


    public void authenticate() {
        logger.log("Cashier: " + cashier.getUsername() + " logged out");
        cashier = CashierAuthenticator.conductUserLogin();
        logger.log("Cashier: " + cashier.getUsername() + " logged in");

    }


    public int requestSaleId() {
        ILogger.logToConsole("Enter the sale id:");

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        int i = 0;
        try {
             i = Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            ILogger.logToConsole("Invalid SaleID Provided.");
        }

        return i;
    }


    public  String requestItemNameToDelete() {
        printItemsInCurrentSale();
        ILogger.logToConsole("Enter the name of the item to remove");

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        try {
            return reader.readLine();
        } catch (Exception e) {
            ILogger.logToConsole("Invalid SaleID Provided.");
        }

        return "";
    }


    public boolean deleteFullSale() {
        ILogger.logToConsole("Enter 1 to delete the entire sale or enter anyother number to select returning specific item.");

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        boolean response = false;
        try {
            if(reader.readLine().equals("1")) {
                response = true;
            }
        } catch (Exception e) {
            ILogger.logToConsole("Invalid SaleID Provided.");
        }

        return response;
    }

}
