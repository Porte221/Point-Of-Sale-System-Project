package com.assignment.seis602.register;

import com.assignment.seis602.cashier.CashierAuthenticator;
import com.assignment.seis602.inventory.Inventory;
import com.assignment.seis602.item.Item;
import com.assignment.seis602.logging.ILogger;
import com.assignment.seis602.logging.RegisterLog;
import com.assignment.seis602.sale.RecordedSales;
import com.assignment.seis602.sale.Sale;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * @Author Ryan Poorman
 * @Description PoSRegister is the focul point of the application, as it handles all requests and has relationships with nearly all other
 * entities of the application.
 */
public class PoSRegister extends PoSRegisterUI implements IPoSRegister {

    public PoSRegister() {
        this.registerId = new Random().nextInt();
        this.logger = new RegisterLog(1);
        logger.log("Register: " + registerId + " has been started");

        this.inventory = new Inventory();
        this.inventory.generateInventoryItems();
        this.cashier = CashierAuthenticator.conductUserLogin();
        logger.log("Cashier: " + "" + " logged in");
    }


    @Override
    public void addItem() {
        printAvailableItems();
        ILogger.logToConsole("\n" +
                "\nEnter item name: ");
        Item itemToAdd = null;
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        try {
            String itemName = reader.readLine();

            if (inventory.containsAvailableItem(itemName)) {
                itemToAdd = inventory.getInventoryItem(itemName).getItem();
                saleItem.addItem(itemToAdd);
                inventory.adjustInventory(itemName, 'R');
            } else {
                ILogger.logToConsole("Item not available.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.log("Item: " + itemToAdd + " was added to the sale: " + saleItem.getSaleID() + " at register #" + registerId);
    }

    @Override
    public void removeItem() {

        ILogger.logToConsole("\n" +
                "\nEnter item name: ");
        Item itemToRemove = null;


        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        try {

            String itemName = reader.readLine();
            itemToRemove = inventory.getInventoryItem(itemName).getItem();
            inventory.adjustInventory(itemName, 'A');
            saleItem.removeItem(itemToRemove);

        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.log("Item: " + itemToRemove + " was added to the sale: " + saleItem.getSaleID() + " at register #" + registerId);
    }

    @Override
    public void cancelOrder() {
        logger.log("Sale was canceled: " + saleItem.getSaleID() + " at register #" + registerId);
        ILogger.logToConsole("Sale was canceled: " + saleItem.getSaleID());
        saleItem.cancelSale(inventory);
        saleItem = null;
    }

    @Override
    public void checkout() {
        printReceipt();
        RecordedSales.archiveSale(saleItem.getSaleID(), saleItem);
        logger.log("Sale: " + saleItem.getSaleID() + " has been checked out" + " at register #" + registerId);
    }

    @Override
    public void shutdown() {
        logger.log("Register is shutting down as requested...");
        System.exit(1);
    }

    @Override
    public void startup() {
        String response = "";

        try {
            do {
                response = displayUIOptions();

                if (response.equals("1")) {
                    startOrder();
                } else if (response.equals("2")) {
                    removePreviousSale();
                } else if (response.equals("3")) {
                    authenticate();
                } else if (response.equals("4")) {
                    System.exit(1);
                } else if (response.equals("5")) {
                    generateReport();
                } else if (response.equals("6")) {
                    ILogger.logToConsole("Please enter a keyword or phrase to generate a detailed report.");
                    generateDetailedReport(new BufferedReader(new InputStreamReader(System.in)).readLine());
                } else if (response.equals("7")) {
                    generateInventoryReport();
                }
            } while (true);
        } catch (Exception e) {

        }
    }
    @Override
    public void removePreviousSale() {
        int saleId = requestSaleId();
        saleItem = RecordedSales.getArchivedSale(saleId);
        if(saleItem != null) {
           if(deleteFullSale()) {
               RecordedSales.cancelSale(saleId, inventory);
           } else {
               requestItemNameToDelete();
           }
        }
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


    /**
     * Returns an Image object that can then be painted on the screen.
     * The url argument must specify an absolute <a href="#{@link}">{@link URL}</a>. The name
     * argument is a specifier that is relative to the url argument.
     * <p>
     * This method always returns immediately, whether or not the
     * image exists. When this applet attempts to draw the image on
     * the screen, the data will be loaded. The graphics primitives
     * that draw the image will incrementally paint on the screen.
     *
     * @param  url  an absolute URL giving the base location of the image
     * @param  name the location of the image, relative to the url argument
     * @return the image at the specified URL
     * @see         Image
     */
}
