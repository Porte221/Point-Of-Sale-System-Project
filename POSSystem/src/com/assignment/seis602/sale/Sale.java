package com.assignment.seis602.sale;

import com.assignment.seis602.inventory.Inventory;
import com.assignment.seis602.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Sale implements IfcSale {
    private Inventory invObj;





    //Validate the Sale ID from SaleMap and use accordingly
    public boolean isSaleIDValid(int SaleID) {
        //Check across the SaleMap for the SaleID and return true if found.
        //SaleID will be the Key for this MAP.
        return false;
    }

    @Override
    public boolean returnItemOnSale(int SaleId) {
        //Using the Sale ID , first check if the Sale is Valid by calling isSaleIDValid method.
        //If Sale found to be valid, pull up all the items against the sale from the SaleItemMap.
        //Ask user to selected the items to return on the sale.
        //Build a new HashMap<Item,Quantity>
        //Call the adjust Inventory method with type of adjustment 'A' to add items back to inventory
        //calculate the amount of sale, update the sale object variable SALEAMOUNT with the remaining amount of net sale.

        //Open item	--//Now access the SALE ITEM MAP using the sale id and update the items object to keep the Sale Vs Items relationship current after adjustment.


        // Before implementing step "Open Item" , We need to find a way to keep track of quantity of items against a sale made.
        // as per current design we are keeping track of Sale VS Item relationship in SaleMAP but not any quantities of items.
        // This will cause an issue when a return is made and not all the quantity for an items is returned.
        //

        return false;
    }


    public void addItem(Item item) {

    }


    public void removeItem(Item item) {

    }


        private int saleID;
        private double saleAmount;
        private Map<String, Item> saleItems;

        public Sale() {
            saleID = new Random().nextInt();
            saleItems = new HashMap();
        }

        public void addItems(Item item) {

        }


        public void removeItems(Item item) {

        }

        public int getSaleID() {
            return saleID;
        }





}


	/* 1. Create a new sale object.
		   * call inventory to get the list of available items using inventory objects
		   2. choose options to selected items
		   3. enter quantity for the item
		   4. store the object as a HashMap in memory [HashMap<Item,Integer> itemMap]
		   5. add the sale amount for all the items selected and update saleAmount variable.
		   6. delegate a call to adjustInventoryMethod() , pass the hashMap , inv object and type of adjustment
		   7. ONce sale is completed , add the sale object to saleMap.
		*/




