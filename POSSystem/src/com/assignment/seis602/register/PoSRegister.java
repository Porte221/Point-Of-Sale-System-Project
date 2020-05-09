package com.assignment.seis602.register;

import com.assignment.seis602.cashier.Cashier;
import com.assignment.seis602.inventory.Inventory;
import com.assignment.seis602.logging.PoSLogger;
import com.assignment.seis602.logging.RegisterLog;
import com.assignment.seis602.sale.Sale;

import java.util.Random;
/*
        1. The register needs to be able to add items to the sale
        2. The register needs to be able to remove items to the sale
        3. The register checkout a sale
        4. Cashier needs to be implemented so the register can switch who is logged in
 */

public class PoSRegister<T> implements IPoSRegister<T> {
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
        this.saleItem = new Sale();
        logger.log("Cashier: " + ""+ " logged in");


    }

    @Override
    public void startOrder() {
        saleItem.createSale(inventory);
    }

    @Override
    public void addItem(T itemToAdd) {
        logger.log("Item: " + itemToAdd + " was added to the sale: " + saleItem.getSaleID());
    }

    @Override
    public void removeItem(T itemToRemove) {
        logger.log("Item: " + itemToRemove + " was added to the sale: " + saleItem.getSaleID());
    }

    @Override
    public void cancelOrder() {
        saleItem.cancelSale(saleItem.getSaleID());
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
}
