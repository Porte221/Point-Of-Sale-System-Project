package com.assignment.seis602.sale;

import java.util.*;
import java.util.concurrent.*;
import com.assignment.seis602.inventory.Inventory;
import com.assignment.seis602.sale.IfcSale;
import com.assignment.seis602.item.Item;

public class Sale implements IfcSale
{
	private int saleID;
    private double saleAmount;	
    //private Inventory invObj;
    private static HashMap<Integer,Sale> SaleLookUpMap; // Will be used a primary Sale Object collection to check the sale id
                                                 // and update to remove sale.
    
    private static ConcurrentHashMap<Integer,HashMap<Item,Integer>> SaleItemMap;    //Use this Map as static collection for storing Sale ID VS Items in the sale,quantity of items.
                                                                                    //This will be used to identify Sale and its items with quantities
    
   //Register should pass the same inventory object which was used to initialize inventory when application started as the Inv is not static
   //and needs to bound to single instance for any changes to reflect 
      
    
    public Sale()
    {
    	// Generate sale id randomly
    }
   
	public static ConcurrentHashMap<Integer, HashMap<Item, Integer>> getSaleItemMap() 
	{
		return SaleItemMap;
	}
	
	// Returns MAP of items related to the SALE.
	public HashMap<Integer,Sale> getSaleMap()
	{
	//return a new HashMap object by searching the SaleID as Key in the MAP.
		
	 return SaleLookUpMap;
	}

	public boolean createSale(Inventory invObj)
	{	
		
		/* 1. Create a new sale object.
		   *  Call inventory to get the list of available items using inventory objects.
		   2. Display it to user to choose options for selecting items 
		   3. Once User selects an item , ask for entering the quantity for the items.
		   4. store the item and quantity as a new HashMap called tempSaleItems. In this HashMap, [Key] will be the Item Object and value will be quantity.
		   5. Now , pass the tempSaleItems hashMap to static class variable of type ConcurrentHashMap named as SaleItemMap, with [Key] as Sale ID and [Value] as tempSaleItems hashMap created in step 4.
		   5. Now update the Sale Amount variable by adding all the price of all the items selected and quantities
		   6. Now trigger a call to adjustInventoryMethod() , pass the tempSaleItems and type of adjustment 'R' means taking items out of inventory , 'A' means adding back to 
		      Inventory.
		   7. Now as a final step add the Sale object created in step 1 to SaleLookUpMap.
		   
		*/
	 return false;

	}
	
	//Cancel sale item	
	public boolean cancelSale(int saleId,Inventory invObj)
	{				
		//Retrieve the Sale object from the SaleMap and fetch all the items for that sale.
		//Once all the items are fetched , call the adjust inventory method to add items back to Inventory
		//Remove the Sale object from the Map

		if(this.isSaleIDValid(saleId))
		{
			//Get Sale and items relationship and pass it down to adjustInventory method for making adjustments
			
			invObj.adjustInventory(SaleItemMap.get(saleId), 'A');
			
			//After adjusting the Sale needs to be removed
				
			SaleItemMap.remove(saleId);
			SaleLookUpMap.remove(saleId);	
		}
		else
		{
			System.out.println("Sale Id is not valid. Enter valid Sale id.");
		}
		
		return false;
	}
	

	//Validate the Sale ID from SaleMap and use accordingly
    public boolean isSaleIDValid(int saleId)
    {
    	//Check across the SaleMap for the SaleID and return true if found.
    	//SaleID will be the Key for this MAP.
    	
    	if(this.getSaleMap().containsKey(saleId))
    	{
    		System.out.println("Sale ID found in the SaleMap . processing will continue");
    		return true;
    	}
    	else
    	{
    	return false;
    	}
    }

	@Override
	public boolean ReturnItemOnSale(int SaleId,Inventory invObj) 
	{
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
	
	// May be Have a method to Write the state of Sale File as a Flat file ??
}



