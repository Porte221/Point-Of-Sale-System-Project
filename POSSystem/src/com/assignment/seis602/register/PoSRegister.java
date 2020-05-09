package com.assignment.seis602.register;

import com.assignment.seis602.cashier.Cashier;
import com.assignment.seis602.inventory.Inventory;
import com.assignment.seis602.item.Item;
import com.assignment.seis602.logging.PoSLogger;
import com.assignment.seis602.logging.RegisterLog;
import com.assignment.seis602.sale.Sale;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;
/*
        1. The register needs to be able to add items to the sale
        2. The register needs to be able to remove items to the sale
        3. The register checkout a sale
        4. Cashier needs to be implemented so the register can switch who is logged in
 */

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
        this.cashier = new Cashier();
        logger.log("Cashier: " + "" + " logged in");


    }

    @Override
    public void startOrder() {
        saleItem = new Sale();

        String response = "";

        try {
            do {
                response = displayUIOrderOptions();
                System.out.println(response.equals("3"));

                if (response.equals("1")) {
                  handleItemAddition();
                } else if (response.equals("2")) {
                    System.out.println(2);
                } else if (response.equals("3")) {
                    System.out.println(3);
                    break;
                } else if (response.equals("4")) {
                    System.out.println(4);
                    break;
                }
            } while (true);
        } catch (Exception e) {

        }

    }

    private void handleItemAddition() throws IOException {
        System.out.println("Enter item name: ");


        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        String itemName = reader.readLine();

        //if item can be added
        //additem(item)
        //else inform user
        addItem(new Item());
    }


    private void handleItemRemoval() throws IOException {
        System.out.println("Enter item name: ");


        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        String itemName = reader.readLine();

        //if item can be added
        //additem(item)
        //else inform user
        removeItem(new Item());
    }



    @Override
    public void addItem(Item itemToAdd) {

        logger.log("Item: " + itemToAdd + " was added to the sale: " + saleItem.getSaleID());
    }

    @Override
    public void removeItem(Item itemToRemove) {
        logger.log("Item: " + itemToRemove + " was added to the sale: " + saleItem.getSaleID());
    }

    @Override
    public void cancelOrder() {
        logger.log("Sale was canceled: " + saleItem.getSaleID());
        saleItem = null;
    }

    @Override
    public void checkout() {
        logger.log("Sale: " + saleItem.getSaleID() + " has been checked out");
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



    private void reauthenticate() {
        logger.log("Cashier: " + "" + " logged out");
        cashier = new Cashier();
        logger.log("Cashier: " + "" + " logged in");

    }

    private String displayUIOrderOptions() throws IOException {
        System.out.println("1. Add Item");
        System.out.println("2. Remove Item");
        System.out.println("3. Cancel Sale");
        System.out.println("4. Checkout");


        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        return reader.readLine();
    }



}
