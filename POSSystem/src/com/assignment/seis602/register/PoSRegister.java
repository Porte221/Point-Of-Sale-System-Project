package com.assignment.seis602.register;

import com.assignment.seis602.cashier.Cashier;
import com.assignment.seis602.cashier.CashierAuthenticator;
import com.assignment.seis602.inventory.Inventory;
import com.assignment.seis602.item.InventoryItem;
import com.assignment.seis602.item.Item;
import com.assignment.seis602.item.SaleItem;
import com.assignment.seis602.logging.PoSLogger;
import com.assignment.seis602.logging.RegisterLog;
import com.assignment.seis602.sale.Sale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Random;


public class PoSRegister implements IPoSRegister {
    Inventory inventory = null;
    Cashier cashier = null;
    Integer registerId = null;
    Sale saleItem = null;
    PoSLogger logger = null;

    public PoSRegister() {
        this.registerId = new Random().nextInt();
        this.logger = new RegisterLog(1);
        logger.log("Register: " + registerId + " has been started");

        this.inventory = new Inventory();
        this.inventory.generateInventoryItems();
        this.inventory.printInventory();
        this.cashier = CashierAuthenticator.conductUserLogin();
        logger.log("Cashier: " + "" + " logged in");
    }

    @Override
    public void startOrder() {
        saleItem = new Sale();

        String response = "";

        try {
            do {
                response = displayUIOrderOptions();

                if (response.equals("1")) {
                    addItem();
                } else if (response.equals("2")) {
                    removeItem();
                } else if (response.equals("3")) {
                    cancelOrder();
                    break;
                } else if (response.equals("4")) {
                    checkout();
                    break;
                } else if (response.equals("5")) {
                    printItemsInCurrentSale();
                }
            } while (true);
        } catch (Exception e) {

        }

    }


    @Override
    public void addItem() {
        printAvailableItems();
        System.out.println("\n" +
                "\nEnter item name: ");
        Item itemToAdd = null;
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        try {
            String itemName = reader.readLine();

            if (inventory.containsAvailableItem(itemName)) {
                itemToAdd = inventory.getInventoryItem(itemName);
                saleItem.addItem(itemToAdd);
                inventory.adjustInventory(itemName, 'R');
            } else {
                System.out.println("Item not available.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.log("Item: " + itemToAdd + " was added to the sale: " + saleItem.getSaleID());
    }

    @Override
    public void removeItem() {

        System.out.println("\n" +
                "\nEnter item name: ");
        Item itemToRemove = null;


        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        try {

            String itemName = reader.readLine();
            itemToRemove = inventory.getInventoryItem(itemName);
            saleItem.removeItem(itemToRemove);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //update inventory
        logger.log("Item: " + itemToRemove + " was added to the sale: " + saleItem.getSaleID());
    }

    @Override
    public void cancelOrder() {
        logger.log("Sale was canceled: " + saleItem.getSaleID());
        System.out.println("Sale was canceled: " + saleItem.getSaleID());
        saleItem.cancelSale(inventory);
        saleItem = null;
    }

    @Override
    public void checkout() {
        printReceipt();
        logger.log("Sale: " + saleItem.getSaleID() + " has been checked out");
    }

    private void printReceipt() {
        System.out.println("\n" +
                "\n--Point of Sale Receipt--");

        printItemsInCurrentSale();
        System.out.println("Total Price: " + saleItem.getSaleAmount());
    }

    private void printItemsInCurrentSale() {
        Map<String, SaleItem> items =  saleItem.getSaleItems();
        for (Map.Entry<String, SaleItem> mapElement : items.entrySet()) {
            String itemName = (String)mapElement.getKey();
            System.out.println(mapElement.getValue().getItemCount() +" " + itemName + " || Price: $" + mapElement.getValue().getItem().getUnitPrice()*mapElement.getValue().getItemCount());
        }
    }


    private void printAvailableItems() {


        Map<String, InventoryItem> items = inventory.getAvailableInventoryItems();
        InventoryItem i = null;
        for (Map.Entry<String, InventoryItem> mapElement : items.entrySet()) {
            String itemName = (String)mapElement.getKey();
            System.out.println("Product Name: " + itemName + " || Price: $" + mapElement.getValue().getItem().getUnitPrice());
        }
        System.out.println("Running Price: " + saleItem.getSaleAmount());
    }

    @Override
    public void shutdown() {
        logger.log("Register is shutting down as requested...");
        System.exit(1);
    }

    public void generateReport() {
        logger.generateReport();
    }

    public void generateDetailedReport(String employeeName) {
        logger.generateDetailedReport(employeeName);
    }

    public void authenticate() {
        logger.log("Cashier: " + cashier.getUsername() + " logged out");
        cashier = CashierAuthenticator.conductUserLogin();
        logger.log("Cashier: " + cashier.getUsername() + " logged in");

    }

    private String displayUIOrderOptions() throws IOException {
        System.out.println("\n" +
                "\n1. Add Item");
        System.out.println("2. Remove Item");
        System.out.println("3. Cancel Sale");
        System.out.println("4. Checkout");
        System.out.println("5. View Current Sale");



        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        return reader.readLine();
    }


}
