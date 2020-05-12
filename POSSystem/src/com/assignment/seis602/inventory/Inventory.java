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
	private static final long serialVersionUID = 4092437609098686219L;
    private Item ItemObj;
    private int StockPerItem;
    private double itemThreshold;
    private OrderStock OrderItem;
    private ConcurrentHashMap<String, Inventory> InvHashMap = new ConcurrentHashMap<String, Inventory>(20, 0.75F);
    private static String outputDataStateLocation = "resources/DataStateFile/ObjectStateFile.txt";
    private static String DataStateLocation = "resources/DataStateFile/ObjectStateFile.txt";

    public Inventory() {

    }

    Inventory(Item itm, double threshold, OrderStock ordrObj, int stock) {
        this.ItemObj = itm;
        this.itemThreshold = threshold;
        this.OrderItem = ordrObj;
        this.StockPerItem = stock;
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
    private void createInventoryForItems() throws IOException 
    {
        //This file be used as a data-store to perform IO operations based on when an item is updated.

        File objectFile = new File(DataStateLocation);
        FileInputStream fin = null;
        ObjectInputStream ois = null;


        if (objectFile.exists()) 
        {
            //Read current state from the existing Object state file.
            fin = new FileInputStream(objectFile);
            ois = new ObjectInputStream(fin);

            try 
            {
                InvHashMap = (ConcurrentHashMap<String, Inventory>) ois.readObject();

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            } finally {
                ois.close();
                fin.close();
            }
        } else {
            //Create a new Object State file using the items file
            ArrayList<Item> itemList = Item.getItemList();
            int minVStockValue = 10;

            for (Item e : itemList) {
                InvHashMap.put(e.getItemName(), new Inventory(e, Math.round((minVStockValue) * 0.40), null, (int) (Math.random() * 5) + minVStockValue));
            }

            writeInventoryFile(outputDataStateLocation, this);
        }
    }

    private boolean createReStockOrder() {

        Iterator<Entry<String, Inventory>> itr = this.getAvailableInventoryItems().entrySet().iterator();

        ArrayList<Item> itemList = Item.getItemList();
        Item itemObjForOrder = null;

        if (itr != null && itemList != null) 
        {
            while (itr.hasNext()) 
            {
                Map.Entry<String, Inventory> mapElement = (Map.Entry<String, Inventory>) itr.next();
                Inventory invObj = mapElement.getValue();

                if (invObj.StockPerItem < invObj.itemThreshold) 
                {
                    System.out.println("Item stock found to be less than item threshold defined...creating restock order");

                    for (Item e : itemList) 
                    {
                        if (e.getItemName().equalsIgnoreCase(mapElement.getKey())) 
                        {
                            System.out.println("Item Found");
                            itemObjForOrder = e;
                        }
                    }
                    
                   if (itemObjForOrder != null) 
                    {
                        invObj.OrderItem = new OrderStock(itemObjForOrder, "ABC", 10);
                    } else {
                        System.out.println("Order already exist ,no new order will be generated for item :" + mapElement.getKey());
                    }
                  }
             }
            // Rewriting the file
            writeInventoryFile(outputDataStateLocation, this);
            
            return true;
        } 
        else {
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
    	
    	boolean canAdjustItems=false;

        switch (typeOfAdjustment)
        {
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

                        if (tempInv.getStockPerItem() - quantityPurchased >= 0) 
                        {
                        	canAdjustItems =true;
                            tempInv.setStockPerItem(tempInv.getStockPerItem() - quantityPurchased);

                            System.out.println("Item - " + ItemObj + " is Updated.");
                        } 
                        else 
                        {
                        	canAdjustItems=false;
                            System.out.println("cannot update the stock less than 0");
                           
                        }
                    }
                }

                //Make a call to CreateReStockOrder() by passing the updated state of inventory.

                if(canAdjustItems)
                {
                 if (this.createReStockOrder()) 
                 {
                    System.out.println("Restocking was successfull");
                 }
                 else 
                 {
                    System.out.println("Restocking was unsuccessfull");
                 }
                }
                else
                {
                 System.out.println("No items will be adjusted .. Enter items to purchased with in stock");
                 
                }
                break;

            case 'A':
                // 'A' refers to add items from return or cancellation of sale to Inventory

                Iterator<Entry<Item, Integer>> itr2 = itemMap.entrySet().iterator();

                while (itr2.hasNext()) {
                    Map.Entry<Item, Integer> mapElement = (Map.Entry<Item, Integer>) itr2.next();
                    Item ItemObj = mapElement.getKey();
                    Integer quantityPurchased = mapElement.getValue();

                    if (this.getAvailableInventoryItems().containsKey(ItemObj.getItemName())) 
                    {
                        System.out.println("Item has been found in the Inventory Map...Now add items back to the inventory hashmap");

                        Inventory tempInv = this.InvHashMap.get(ItemObj.getItemName());

                        tempInv.setStockPerItem(tempInv.getStockPerItem() + quantityPurchased);

                        System.out.println("Item - " + ItemObj + " is Updated.");
                        
                        canAdjustItems=true;
                    } 
                    else 
                    {
                        System.out.println("Item not found in the inventory , cannot add items back to inventory");
                        canAdjustItems=false;
                    }

                }

                //Make a call to CreateReStockOrder() by passing the updated state of inventory.

                if(canAdjustItems)
                {
                 if (this.createReStockOrder()) 
                 {
                    System.out.println("Restocking was successfull");
                 }
                 else 
                 {
                    System.out.println("Restocking was unsuccessfull");
                 }
                }
                else
                {
                 System.out.println("No items will be adjusted .. Enter items to purchased with in stock");
                 
                }
                break;
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
