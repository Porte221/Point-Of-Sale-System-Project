package com.assignment.seis602.inventory;

import com.assignment.seis602.item.InventoryItem;
import com.assignment.seis602.item.Item;
import com.assignment.seis602.logging.ILogger;
import com.assignment.seis602.orderStock.OrderStock;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class Inventory implements Serializable, IfcInventory {
    /**
     *
     */
    private static final long serialVersionUID = 12002112L;
    private Map<String, InventoryItem> invHashMap = new ConcurrentHashMap<String, InventoryItem>(20, 0.75F);
    private static String outputDataStateLocation = "resources/DataStateFile/ObjectStateFile.txt";
    private static String DataStateLocation = "resources/DataStateFile/ObjectStateFile.txt";

    public Inventory() {

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

        File objectFile = new File(DataStateLocation);
        FileInputStream fin = null;
        ObjectInputStream ois = null;


        //  if (objectFile.exists()) {
        if (false) {
            //Read current state from the existing Object state file.
            fin = new FileInputStream(objectFile);
            ois = new ObjectInputStream(fin);

            try {
                invHashMap = (ConcurrentHashMap<String, InventoryItem>) ois.readObject();

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            } finally {
                ois.close();
                fin.close();
            }
        } else {
            //Create a new Object State file using the items file
            new Item().StartCreateItems();
            ArrayList<Item> itemList = Item.getItemList();
            int minVStockValue = 10;

            for (Item e : itemList) {
                invHashMap.put(e.getItemName(), new InventoryItem(e, Math.round((minVStockValue) * 0.40), new OrderStock(), (int) (Math.random() * 5) + minVStockValue));
            }

            writeInventoryFile(outputDataStateLocation, this);
        }
    }

    private boolean createReStockOrder() {

        Iterator<Entry<String, InventoryItem>> itr = this.getAvailableInventoryItems().entrySet().iterator();

        ArrayList<Item> itemList = Item.getItemList();
        Item itemObjForOrder = null;

        if (itr != null && itemList != null) {
            while (itr.hasNext()) {
                Map.Entry<String, InventoryItem> mapElement = (Map.Entry<String, InventoryItem>) itr.next();
                InventoryItem invObj = mapElement.getValue();

                if (invObj.getStockPerItem() < invObj.getItemThreshold()) {
                    ILogger.logToConsole("Item stock found to be less than item threshold defined...creating restock order");

                    for (Item e : itemList) {
                        if (e.getItemName().equalsIgnoreCase(mapElement.getKey())) {
                            ILogger.logToConsole("Item Found");
                            itemObjForOrder = e;
                        }
                    }

                    if (itemObjForOrder != null) {
                        invObj.setOrderItem(new OrderStock(itemObjForOrder, "ABC", 10));
                    } else {
                        //  ILogger.logToConsole("Order already exist ,no new order will be generated for item :" + mapElement.getKey());
                    }
                }
            }
            // Rewriting the file
            writeInventoryFile(outputDataStateLocation, this);

            return true;
        } else {
            return false;
        }
    }

    // Read data from Inventory list
    public Map<String, InventoryItem> getAvailableInventoryItems() {
        return this.invHashMap;
    }


    public void adjustInventory(String itemName, char typeOfAdjustment) {

        boolean canAdjustItems = false;
        InventoryItem tempInv = getInventoryItem(itemName);

        switch (typeOfAdjustment) {
            case 'R':
                //'R' refers to removing items of sale from Inventory


                if (this.getAvailableInventoryItems().containsKey(tempInv.getItem().getItemName())) {

                    if (this.getAvailableInventoryItems().containsKey(tempInv.getItem().getItemName())) {
                        // ILogger.logToConsole("Item has been found in the Inventory Map...Now update the inventory hashmap");

                        if (tempInv.getStockPerItem() - 1 >= 0) {
                            canAdjustItems = true;
                            tempInv.setStockPerItem(tempInv.getStockPerItem() - 1);

                        } else {
                            canAdjustItems = false;
                            // ILogger.logToConsole("cannot update the stock less than 0");

                        }
                    }
                }


                //Make a call to CreateReStockOrder() by passing the updated state of inventory.

                if (canAdjustItems) {
                    if (this.createReStockOrder()) {
                        //  ILogger.logToConsole("Restocking was successfull");
                    } else {
                        //  ILogger.logToConsole("Restocking was unsuccessfull");
                    }
                } else {
                    // ILogger.logToConsole("No items will be adjusted .. Enter items to purchased with in stock");

                }
                break;

            case 'A':
                // 'A' refers to add items from return or cancellation of sale to Inventory


                if (this.getAvailableInventoryItems().containsKey(tempInv.getItem().getItemName())) {
                    // ILogger.logToConsole("Item has been found in the Inventory Map...Now add items back to the inventory hashmap");


                    tempInv.setStockPerItem(tempInv.getStockPerItem() + 1);

                    //  ILogger.logToConsole("Item - " + ItemObj + " is Updated.");

                    canAdjustItems = true;
                } else {
                    //  ILogger.logToConsole("Item not found in the inventory , cannot add items back to inventory");
                    canAdjustItems = false;
                }


        }

        //Make a call to CreateReStockOrder() by passing the updated state of inventory.

        if (canAdjustItems) {
            if (this.createReStockOrder()) {
                ILogger.logToConsole("Restocking was successfull");
            } else {
                ILogger.logToConsole("Restocking was unsuccessfull");
            }
        } else {
            ILogger.logToConsole("No items will be adjusted .. Enter items to purchased with in stock");

            if (canAdjustItems) {
                if (this.createReStockOrder()) {
                    //  ILogger.logToConsole("Restocking was successfull");
                } else {
                    // ILogger.logToConsole("Restocking was unsuccessfull");
                }
            } else {
                // ILogger.logToConsole("No items will be adjusted .. Enter items to purchased with in stock");

            }
        }

    }


    //This method is to be called when writing Inventory File
    public void writeInventoryFile(String PathToWrite, Inventory inv) {
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;

        try {

            fout = new FileOutputStream(new File(PathToWrite));
            oos = new ObjectOutputStream(fout);

            //Write the current state of inventory

            oos.writeObject(inv.getAvailableInventoryItems());

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                oos.close();
                fout.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }


    public boolean containsAvailableItem(String itemName) {
        InventoryItem invItem = invHashMap.get(itemName);
        if (invItem == null) {
            return false;
        }
        return invItem.getStockPerItem() > 0;
    }


    public InventoryItem getInventoryItem(String itemName) {
        return invHashMap.get(itemName);
    }


    public void printDetailedInventory() {
        Iterator it = invHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ILogger.logToConsole("Name: " + pair.getKey() + " || Quantity: " + ((InventoryItem) pair.getValue()).getStockPerItem()
                    + " || Threshold: " + ((InventoryItem) pair.getValue()).getItemThreshold()
                    + " || Supplier: " + ((InventoryItem) pair.getValue()).getOrderItem().getSupplierName()
                    + " || On Order: " + ((InventoryItem) pair.getValue()).getOrderItem().getOrderQuantity());
        }
    }


}
