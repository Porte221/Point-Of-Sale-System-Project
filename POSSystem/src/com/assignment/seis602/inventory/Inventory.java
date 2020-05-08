package com.assignment.seis602.inventory;

import com.assignment.seis602.inventory.IfcInventory;
import java.util.concurrent.ConcurrentHashMap;
import com.assignment.seis602.item.Item;
import java.util.*;
import java.util.Map.Entry;
import java.io.*;
import com.assignment.seis602.orderStock.OrderStock;

public class Inventory implements Serializable,IfcInventory
{
	private Item ItemObj;	
	private int StockPerItem;
	private double itemThreshold;
    private OrderStock OrderItem;
    private ConcurrentHashMap<String,Inventory> InvHashMap = new ConcurrentHashMap<String,Inventory>(20,0.75F);
    private static String outputLocation="C:\\Users\\asharm145\\workspace\\POSSystem\\resources\\OutputFiles\\InventoryFile.txt";        
    private static String DataStateLocation="C:\\Users\\asharm145\\workspace\\POSSystem\\resources\\DataStateFiles\\ObjectStateFile.txt";        
    
    public Inventory()
    {
    	
    }
    
    Inventory(Item itm,double threshold,OrderStock ordrObj,int stock)
    {
    	this.ItemObj=itm;
    	this.itemThreshold=threshold;
    	this.OrderItem=ordrObj;
    	this.StockPerItem=stock;
    }    
    
    public void generateInventoryItems()
    {
    	try {
			createInventoryForItems();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //This Methods are to be called only once at the start of the application when user log in is successfully.
    //Load data related to items in inventory    
    private void createInventoryForItems() throws IOException
    {
      //This file be used as a data-store to perform IO operations based on when an item is updated.
    	
      //Now create Inventory for these objects
     	File objectFile = new File(DataStateLocation);
     	FileOutputStream fout=null;
     	ObjectOutputStream oos=null;
     	FileInputStream fin=null;
     	ObjectInputStream ois=null;
     	
     	
     	if (objectFile.exists())
     	{
     		//Read current state from the existing Object state file.
     		
     		fin = new FileInputStream(objectFile);
            ois = new ObjectInputStream(fin);
            
            try
            {
             InvHashMap= (ConcurrentHashMap<String,Inventory>)ois.readObject();  
             
            //check if the item is less than the threshold, if found then create a re-stock order.
             
            
            }
            catch(IOException | ClassNotFoundException ex)
            {
            	ex.printStackTrace();
            }
            finally
            {
            	ois.close();
            	fin.close();
            }     
            
            Iterator<Entry<String, Inventory>> itr = InvHashMap.entrySet().iterator();   		
     		while(itr.hasNext())
    		{
    		  Map.Entry<String,Inventory> mapElement = (Map.Entry<String, Inventory>) itr.next();
    		  System.out.println("Key = "+mapElement.getKey()+" , "+" Value = "+mapElement.getValue());   			
    		  //myWriter.write(mapElement.getValue().toString());
    		}  
     	}
     	else
     	{
     	//Create a new Object State file using the items file
     	ArrayList<Item> itemList = new Item().getItemList();
     	int minVStockValue =10;    	
     	
     	for(Item e:itemList)
     	{    		
    	  InvHashMap.put(e.getItemName(),new Inventory(e,Math.round((minVStockValue)*0.40),null,(int)(Math.random()*5)+minVStockValue));
    	} 
     	
     	try
     	{
     		
     	fout = new FileOutputStream(objectFile);
        oos = new ObjectOutputStream(fout);        
        oos.writeObject(InvHashMap);
     	oos.close();
     	fout.close();
     	}
     	catch(IOException ex)
     	{
     		ex.printStackTrace();
     	}
     	finally
     	{
     		oos.close();
     		fout.close();
     	}	     	
      }     	
    }
        
    private boolean createReStockOrder(Inventory inv)
    {
       //if while creating the Inventory data , any     	   	
    	
    	/* Iterate on the whole Inventory Map
    	 * Check the Stock against the Threshold , if found to be less , create a Object of OrderStock class
    	 * 
    	 */
    	
    	
    	return false;
    }
    
    // Read data from Inventory list    
    public ConcurrentHashMap<String,Inventory> getAvailableInventoryItems()
    {
    	//1. Check whether item is in stock
    	//2. Then check whether item is less than threshold if so  start logic to reorder 
    	
     	return this.InvHashMap;
    }    
    
    private void adjustInventory(Inventory inv,HashMap<Item,Integer> itemMap,char typeOfAdjustment)
    {
      //This method will update the inventory based on whether the items are added or removed...
      //Pull up items from the itemList and access the same in InvHashMap collection.
    	
    	switch(typeOfAdjustment)
    	{
    	case 'R':
    		// 'R' refers to removing items of sale from Inventory
    		
    		Iterator<Entry<Item, Integer>> itr = itemMap.entrySet().iterator(); 
    		
     		while(itr.hasNext())
    		{
    		  Map.Entry<Item, Integer> mapElement = (Map.Entry<Item, Integer>) itr.next();
    		  
    		 // inv.InvHashMap.
    		  
    		}  
     		
     		//Make a call to CreateReStockOrder() by passing the updated state of inventory.
    		
    		//break;
    		
    	case 'A':
    		// 'A' refers to add items from return or cancellation of sale to Inventory
    		
    		
    		//Make a call to CreateReStockOrder() by passing the updated state of inventory.
    		
    		break;
    	}
    	
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
	
    public String toString()
    {
    	return this.ItemObj.getItemName()+" , "+this.ItemObj.getItemCatrgory()+" ,"+this.ItemObj.getUnitPrice()+" , "+this.getItemThreshold()+" , "+this.getStockPerItem()+" , "+ this.getOrderItem();
    }
    
    
}
