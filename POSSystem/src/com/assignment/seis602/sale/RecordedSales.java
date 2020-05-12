package com.assignment.seis602.sale;

import com.assignment.seis602.inventory.Inventory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RecordedSales {

    private static ConcurrentHashMap<Integer, Sale> saleArchive;    //Use this Map as static collection for storing Sale ID VS Items in the sale,quantity of items.

    public static Map<Integer, Sale> getArchivedSales() {
        return saleArchive;
    }


    //Cancel sale item
    public boolean cancelSale(int saleId, Inventory invObj) {
        //Retrieve the Sale object from the SaleMap and fetch all the items for that sale.
        //Once all the items are fetched , call the adjust inventory method to add items back to Inventory
        //Remove the Sale object from the Map

        if (this.isSaleIDValid(saleId)) {
            //Get Sale and items relationship and pass it down to adjustInventory method for making adjustments
            saleArchive.get(saleId).cancelSale(invObj);
            saleArchive.remove(saleId);


        } else {
            System.out.println("Sale Id is not valid. Enter valid Sale id.");
        }

        return false;
    }


    public boolean isSaleIDValid(int saleId) {
        if (saleArchive.containsKey(saleId)) {
            System.out.println("Sale ID found. Processing sale deletion");
            return true;
        } else {
            return false;
        }
    }


}
