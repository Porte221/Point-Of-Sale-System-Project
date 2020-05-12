package com.assignment.seis602.inventory;

import com.assignment.seis602.item.InventoryItem;
import com.assignment.seis602.item.Item;
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
        if(false) {
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
                invHashMap.put(e.getItemName(), new InventoryItem(e, Math.round((minVStockValue) * 0.40), null, (int) (Math.random() * 5) + minVStockValue));
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
                    System.out.println("Item stock found to be less than item threshold defined...creating restock order");

                    for (Item e : itemList) {
                        if (e.getItemName().equalsIgnoreCase(mapElement.getKey())) {
                            System.out.println("Item Found");
                            itemObjForOrder = e;
                        }
                    }

                    if (itemObjForOrder != null) {
                        invObj.setOrderItem(new OrderStock(itemObjForOrder, "ABC", 10));
                    } else {
                        System.out.println("Order already exist ,no new order will be generated for item :" + mapElement.getKey());
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
        Item ItemObj = getInventoryItem(itemName);

        switch (typeOfAdjustment) {
            case 'R':
                //'R' refers to removing items of sale from Inventory


                if (this.getAvailableInventoryItems().containsKey(ItemObj.getItemName())) {

                    InventoryItem tempInv = this.invHashMap.get(ItemObj.getItemName());

                    if (tempInv.getStockPerItem() - 1 >= 0) {
                        canAdjustItems = true;
                        tempInv.setStockPerItem(tempInv.getStockPerItem() - 1);

                        System.out.println("Item - " + ItemObj + " is Updated.");
                    } else {
                        canAdjustItems = false;
                        System.out.println("cannot update the stock less than 0");

                    }
                }


                //Make a call to CreateReStockOrder() by passing the updated state of inventory.

                if (canAdjustItems) {
                    if (this.createReStockOrder()) {
                        System.out.println("Restocking was successfull");
                    } else {
                        System.out.println("Restocking was unsuccessfull");
                    }
                } else {
                    System.out.println("No items will be adjusted .. Enter items to purchased with in stock");

                }
                break;

            case 'A':
                // 'A' refers to add items from return or cancellation of sale to Inventory


                if (this.getAvailableInventoryItems().containsKey(ItemObj.getItemName())) {
                    System.out.println("Item has been found in the Inventory Map...Now add items back to the inventory hashmap");

                    InventoryItem tempInv = this.invHashMap.get(ItemObj.getItemName());

                    tempInv.setStockPerItem(tempInv.getStockPerItem() + 1);

                    System.out.println("Item - " + ItemObj + " is Updated.");

                    canAdjustItems = true;
                } else {
                    System.out.println("Item not found in the inventory , cannot add items back to inventory");
                    canAdjustItems = false;
                }

        }

        //Make a call to CreateReStockOrder() by passing the updated state of inventory.

        if (canAdjustItems) {
            if (this.createReStockOrder()) {
                System.out.println("Restocking was successfull");
            } else {
                System.out.println("Restocking was unsuccessfull");
            }
        } else {
            System.out.println("No items will be adjusted .. Enter items to purchased with in stock");

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
            System.out.println(ex);
        } finally {
            try {
                oos.close();
                fout.close();
            } catch (IOException ex) {
                System.out.println(ex);
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


    public Item getInventoryItem(String itemName) {
        return invHashMap.get(itemName).getItem();
    }

    public void printInventory() {
        Iterator it = invHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue().getClass());
        }
    }


}
