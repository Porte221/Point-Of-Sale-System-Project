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

public class Inventory implements Serializable, IfcInventory 
{

/**
 * @Author Aadil Sharma
 * @Description This is the core implementation Class for inventory component, it provides methods to 
 * 
 *       1. GenerateInventory objects from Items list.
 *       2. Adjust the inventory state based on a new sale / return / cancellation operations, 
 *       3. Write the current state of inventory as a serialized object file and deserialize the file while and reading the state.
 *       4. Invoke creation of new Order for Re-stock based on items threshold against specific Item in the inventory.
 *       5. Expose methods to provide inventory data to upstream application component during sale.
 *       6. This class follows a singleton pattern to be initialized at the start time of the application
 *              
 */
	
    private static final long serialVersionUID = 12002112L;
    private Map<String, InventoryItem> invHashMap = new ConcurrentHashMap<String, InventoryItem>(20, 0.75F);
    private static String outputDataStateLocation = "resources/DataStateFile/ObjectStateFile.txt";
    private static String DataStateLocation = "resources/DataStateFile/ObjectStateFile.txt";

    public Inventory() 
    {

    }

    public void generateInventoryItems() 
    {
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

    private void createReStockOrder(String itemName) 
    {
    	 int itemStock = this.getAvailableInventoryItems().get(itemName).getStockPerItem();
    	 double itemThreshold = this.getAvailableInventoryItems().get(itemName).getItemThreshold();
    	 OrderStock checkforOrder = this.getAvailableInventoryItems().get(itemName).getOrderItem();     
            
    	    if ((itemStock < itemThreshold) && checkforOrder.getOrderNumber()==0)
    	    {    
    	    	   	    	
    	    	this.getAvailableInventoryItems().get(itemName).setOrderItem(new OrderStock(this.getAvailableInventoryItems().get(itemName).getItem(), "ABC", 10));	
    	    }
    	    else
    	    {
    	    	//ILogger.logToConsole("Order exist , should not be created again.");
    	    }
       	
            // Rewriting the file to record changes in stock and order.
            writeInventoryFile(outputDataStateLocation, this);
        } 
    
    // Read data from Inventory list
    public Map<String, InventoryItem> getAvailableInventoryItems() {
        return this.invHashMap;
    }


    public void adjustInventory(String itemName, char typeOfAdjustment) 
    {

        boolean canAdjustItems = false;
        InventoryItem tempInv = getInventoryItem(itemName);

        switch (typeOfAdjustment) 
        {
            case 'R':
                //'R' refers to removing items of sale from Inventory

                if (this.getAvailableInventoryItems().containsKey(tempInv.getItem().getItemName())) 
                {
                	if (tempInv.getStockPerItem() - 1 >= 0) {
                            canAdjustItems = true;
                            tempInv.setStockPerItem(tempInv.getStockPerItem() - 1);

                        } else {
                            canAdjustItems = false;
                            // ILogger.logToConsole("cannot update the stock less than 0");
                        }
                }
                
                if (canAdjustItems) 
                {
                    this.createReStockOrder(itemName);
                        //  ILogger.logToConsole("Re-stocking");               
                } 
                else 
                {
                    // ILogger.logToConsole("No Re-Stocking");

                }
                break;

            case 'A':
                // 'A' refers to add items from return or cancellation of sale to Inventory

                if (this.getAvailableInventoryItems().containsKey(tempInv.getItem().getItemName())) 
                {
                    // ILogger.logToConsole("Item has been found in the Inventory Map...Now add items back to the inventory hashmap");
                	tempInv.setStockPerItem(tempInv.getStockPerItem() + 1);

                    //  ILogger.logToConsole("Item - " + ItemObj + " is Updated.");
                	canAdjustItems = true;
                } 
                else 
                {
                    //  ILogger.logToConsole("Item not found in the inventory , cannot add items back to inventory");
                    canAdjustItems = false;
                }
               
                if (canAdjustItems) 
                {
                	this.createReStockOrder(itemName);
                	//ILogger.logToConsole("Re-stocking");
                }
                else 
                {
                   //ILogger.logToConsole("Not Re-stocking");
                }
                break;
        	}     
    }

    public void writeInventoryFile(String PathToWrite, Inventory inv) {
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;

        try {

            fout = new FileOutputStream(new File(PathToWrite));
            oos = new ObjectOutputStream(fout);
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

    public void printDetailedInventory()
    {
    	
        Iterator<?> it = invHashMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String,InventoryItem> pair = (Map.Entry<String,InventoryItem>)it.next();
            
            ILogger.logToConsole("Item Name: " + pair.getKey() + " || Quantity: " + pair.getValue().getStockPerItem()
                    + " || Threshold: " +  pair.getValue().getItemThreshold()
                    + " || Supplier: " + pair.getValue().getOrderItem().getSupplierName()
                    + " || Order Qty: " + pair.getValue().getOrderItem().getOrderQuantity());
        }
    }
}
