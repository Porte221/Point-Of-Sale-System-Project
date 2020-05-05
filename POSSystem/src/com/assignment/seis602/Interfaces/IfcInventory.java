/*
 * author name : Aadil Sharma
 */

package com.assignment.seis602.Interfaces;

import java.util.concurrent.ConcurrentHashMap;
import com.assignment.seis602.Inventory.Inventory;

public interface IfcInventory 
{
	//This method needs to be called when a register is created  to generate the Inventory objects for the Items in the application.
	//Make Sure to call Method StartCreateItems() of Item Interface before calling this method
	public void generateInventoryItems();
	
	//This method is to get the available list of Inventory items while creating a SALE.
	public ConcurrentHashMap<String,Inventory> getAvailableInventoryItems();
}
