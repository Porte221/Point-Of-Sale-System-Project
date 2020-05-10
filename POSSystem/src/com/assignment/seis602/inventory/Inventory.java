package com.assignment.seis602.inventory;

import com.assignment.seis602.item.Item;
import com.assignment.seis602.orderStock.OrderStock;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class Inventory implements Serializable, IfcInventory {
    /**
     *
     */
    private static final long serialVersionUID = 12002112L;
    private Item ItemObj;
    private int StockPerItem;
    private double itemThreshold;
    private OrderStock OrderItem;
    private ConcurrentHashMap<String, Inventory> InvHashMap = new ConcurrentHashMap<String, Inventory>(20, 0.75F);

    //Before running the program ,Change these values as per your local project workspace
    private static String outputLocation = "OutputFiles/InventoryFile.txt";
    private static String DataStateLocation = "DataStateFile/ObjectStateFile.txt";

    public Inventory() {

    }

    Inventory(Item itm, double threshold, OrderStock ordrObj, int stock) {
        this.ItemObj = itm;
        this.itemThreshold = threshold;
        this.OrderItem = ordrObj;
        this.StockPerItem = stock;
        generateInventoryItems();
    }

    public void generateInventoryItems() {
        try {
            createInventoryForItems();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This Methods are to be called only once at the start of the application when user log in is successfully.
    //Load data related to items in inventory
    private void createInventoryForItems() throws IOException {
        //This file be used as a data-store to perform IO operations based on when an item is updated.

        //Now create Inventory for these objects
        File objectFile = new File(DataStateLocation);
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;
        FileInputStream fin = null;
        ObjectInputStream ois = null;


        if (objectFile.exists()) {
            //Read current state from the existing Object state file.

            fin = new FileInputStream(objectFile);
            ois = new ObjectInputStream(fin);

            try {
                InvHashMap = (ConcurrentHashMap<String, Inventory>) ois.readObject();

                //check if the item is less than the threshold, if found then create a re-stock order.

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            } finally {
                ois.close();
                fin.close();
            }
        } else {
            //Create a new Object State file using the items file
            ArrayList<Item> itemList = new Item().getItemList();
            int minVStockValue = 10;

            for (Item e : itemList) {
                InvHashMap.put(e.getItemName(), new Inventory(e, Math.round((minVStockValue) * 0.40), null, (int) (Math.random() * 5) + minVStockValue));
            }

            writeInventoryFile(DataStateLocation, this);
        }
    }

    private boolean createReStockOrder() {
        //if while creating the Inventory data , any

        /* Iterate on the whole updated Inventory Map
         * Check the Stock against the Threshold and check whether an Order already exist,
         * If Order exist, return the control back and do not create a new Order.
         * Else ,if Stock found to be less than threshold, create a Object of OrderStock class
         * update the inventory class variable OrderStock with the object created in earlier step
         *
         * Update the inventory by calling writeInventoryFile and passing the updated inv object method
         */

        //ConcurrentHashMap<String,Inventory> emp = this.getAvailableInventoryItems();

        Iterator<Entry<String, Inventory>> itr = this.getAvailableInventoryItems().entrySet().iterator();

        ArrayList<Item> itemList = Item.getItemList();
        Item itemObjForOrder = null;

        if (itr != null && itemList != null) {
            while (itr.hasNext()) {
                Map.Entry<String, Inventory> mapElement = (Map.Entry<String, Inventory>) itr.next();
                Inventory invObj = mapElement.getValue();

                if (invObj.StockPerItem < invObj.itemThreshold) {
                    System.out.println("Item stock found to be less than item threshold defined...creating restock order");

                    for (Item e : itemList) {
                        if (e.getItemName().equalsIgnoreCase(mapElement.getKey())) {
                            System.out.println("Item Found");
                            itemObjForOrder = e;
                        }
                    }
                    if (itemObjForOrder != null) {
                        invObj.OrderItem = new OrderStock(itemObjForOrder, "ABC", 10);
                    } else {
                        System.out.println("Item not found , no order will be generated for item :" + mapElement.getKey());
                    }
                }

            }
            return true;
        } else {
            return false;
        }
    }

    // Read data from Inventory list
    public ConcurrentHashMap<String, Inventory> getAvailableInventoryItems() {
        return this.InvHashMap;
    }

    public void adjustInventory(HashMap<Item, Integer> itemMap, char typeOfAdjustment) {
        //This method will update the inventory based on whether the items are added or removed...
        //Pull up items from the itemList and access the same in InvHashMap collection.

        switch (typeOfAdjustment) {
            case 'R':
                //'R' refers to removing items of sale from Inventory

                Iterator<Entry<Item, Integer>> itr1 = itemMap.entrySet().iterator();

                while (itr1.hasNext()) {
                    Map.Entry<Item, Integer> mapElement = (Map.Entry<Item, Integer>) itr1.next();
                    Item ItemObj = mapElement.getKey();
                    Integer quantityPurchased = mapElement.getValue();

                    if (this.getAvailableInventoryItems().containsKey(ItemObj.getItemName())) {
                        System.out.println("Item has been found in the Inventory Map...Now update the inventory hashmap");

                        Inventory tempInv = this.InvHashMap.get(ItemObj.getItemName());

                        if (tempInv.getStockPerItem() - quantityPurchased >= 0) {
                            tempInv.setStockPerItem(tempInv.getStockPerItem() - quantityPurchased);

                            System.out.println("Item - " + ItemObj + " is Updated.");
                        } else {
                            System.out.println("cannot update the stock less than 0");
                        }
                    }
                }

                //Make a call to CreateReStockOrder() by passing the updated state of inventory.

                if (this.createReStockOrder()) {
                    System.out.println("Restocking was successfull");
                } else {
                    System.out.println("Restocking was unsuccessfull");
                }
                break;

            case 'A':
                // 'A' refers to add items from return or cancellation of sale to Inventory

                Iterator<Entry<Item, Integer>> itr2 = itemMap.entrySet().iterator();

                while (itr2.hasNext()) {
                    Map.Entry<Item, Integer> mapElement = (Map.Entry<Item, Integer>) itr2.next();
                    Item ItemObj = mapElement.getKey();
                    Integer quantityPurchased = mapElement.getValue();

                    if (this.getAvailableInventoryItems().containsKey(ItemObj.getItemName())) {
                        System.out.println("Item has been found in the Inventory Map...Now add items back to the inventory hashmap");

                        Inventory tempInv = this.InvHashMap.get(ItemObj.getItemName());

                        tempInv.setStockPerItem(tempInv.getStockPerItem() + quantityPurchased);

                        System.out.println("Item - " + ItemObj + " is Updated.");
                    } else {
                        System.out.println("cannot add items back to inventory");
                    }

                }

                //Make a call to CreateReStockOrder() by passing the updated state of inventory.

                if (this.createReStockOrder()) {
                    System.out.println("Restocking was successfull");
                } else {
                    System.out.println("Restocking was unsuccessfull");
                }

                break;
        }
    }

    //This method is to be called when writing Inventory File
    public boolean writeInventoryFile(String PathToWrite, Inventory inv) {
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;

        try {
            fout = new FileOutputStream(new File(PathToWrite));
            oos = new ObjectOutputStream(fout);

            //Write the current state of inventory

            oos.writeObject(inv.getAvailableInventoryItems());
            System.out.println("File Written to location : " + PathToWrite);

        } catch (IOException ex) {
            System.out.println(ex);
        } finally {
            try {
                oos.close();
                fout.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }

        }

        return true;
    }

    public boolean containsAvailableItem(String itemName) {
        return true;
    }

    public Item getItem(String itemName) {
        return null;
    }

    public int getStockPerItem() {
        return StockPerItem;
    }

    public void setStockPerItem(int stockPerItem) {
        StockPerItem = stockPerItem;
    }

    public double getItemThreshold() {
        return itemThreshold;
    }

    public void setItemThreshold(double itemThreshold) {
        this.itemThreshold = itemThreshold;
    }

    public OrderStock getOrderItem() {
        return OrderItem;
    }

    public void setOrderItem(OrderStock orderItem) {
        OrderItem = orderItem;
    }

    public String toString() {
        return this.ItemObj.getItemName() + " , " + this.ItemObj.getItemCategory() + " ," + this.ItemObj.getUnitPrice() + " , " + this.getItemThreshold() + " , " + this.getStockPerItem() + " , " + this.getOrderItem();
    }


}
