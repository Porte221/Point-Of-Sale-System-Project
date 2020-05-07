package com.assignment.seis602.register;

import com.assignment.seis602.cashier.Cashier;
import com.assignment.seis602.inventory.Inventory;
import com.assignment.seis602.logging.PoSLogger;
import com.assignment.seis602.logging.RegisterLog;
import com.assignment.seis602.sale.Sale;

public class PoSRegister<T> implements IPoSRegister<T> {
    Inventory inventory = null;
    Cashier cashier = null;
    String registerId = null;
    Sale saleItem = null;
    PoSLogger logger = null;

    public PoSRegister() {
        this.inventory = new Inventory();
        this.cashier = new Cashier();
        String registerId = null;
        this.saleItem = new Sale();
        this.logger = new RegisterLog(1);
    }

    @Override
    public void startOrder() {
        //todo
    }

    @Override
    public void addItem(T itemToAdd) {
        //todo

    }

    @Override
    public void removeItem(T itemToRemove) {
        //todo

    }

    @Override
    public void cancelOrder() {
        //todo

    }

    @Override
    public void checkout() {
        //todo

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
