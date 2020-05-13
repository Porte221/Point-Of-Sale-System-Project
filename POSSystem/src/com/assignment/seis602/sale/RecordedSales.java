package com.assignment.seis602.sale;

import com.assignment.seis602.inventory.Inventory;
import com.assignment.seis602.item.InventoryItem;
import com.assignment.seis602.item.Item;
import com.assignment.seis602.logging.ILogger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author Ryan Poorman
 * @Description RecordedSales is used to hold previous sales that have been checkout by the register. If a user wishes to return previous items,
 * that data will be stored within this class
 */
public class RecordedSales {

    private static ConcurrentHashMap<Integer, Sale> saleArchive = new ConcurrentHashMap<>();

    public static Map<Integer, Sale> getArchivedSales() {
        return saleArchive;
    }

    public static Sale getArchivedSale(int saleID) {
        try {
            return saleArchive.get(saleID);
        } catch (Exception e) {
            ILogger.logToConsole("Sale does not exist. Please verify sale id.");
            return null;
        }
    }

    public static void archiveSale(int saleId, Sale sale) {
        saleArchive.put(saleId, sale);
    }

    public static void cancelSale(int saleId, Inventory invObj) {

        if (isSaleIDValid(saleId)) {
            saleArchive.get(saleId).cancelSale(invObj);
            saleArchive.remove(saleId);
            ILogger.logToConsole("Sale has been canceled. You are owed: " + saleArchive.get(saleId).getSaleAmount());
        } else {
            ILogger.logToConsole("Sale Id is not valid. Enter valid Sale id.");
        }
    }

    public static void removeItemFromPriorSale(int saleId, Inventory invObj, String itemName) {

        if (isSaleIDValid(saleId)) {
            Sale sale = saleArchive.get(saleId);
            InventoryItem i = invObj.getInventoryItem(itemName);
            sale.removeItem(i.getItem());
            saleArchive.put(saleId, sale);
            ILogger.logToConsole("Sale item has been canceled. You are owed: " + i.getItem().getUnitPrice());
        } else {
            ILogger.logToConsole("Sale Id is not valid. Enter valid Sale id.");
        }
    }


    public static boolean isSaleIDValid(int saleId) {
        if (saleArchive.containsKey(saleId)) {
            ILogger.logToConsole("Sale ID found. Processing sale deletion");
            return true;
        } else {
            return false;
        }
    }


}
