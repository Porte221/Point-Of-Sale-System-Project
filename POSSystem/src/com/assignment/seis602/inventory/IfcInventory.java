/*
 * author name : Aadil Sharma
 */

package com.assignment.seis602.inventory;

import java.util.concurrent.ConcurrentHashMap;

public interface IfcInventory 
{
	//This method needs to be called when a register is created  to generate the inventory objects for the Items in the application.
	//Make Sure to call Method StartCreateItems() of item Interface before calling this method
	public void generateInventoryItems();
	
	//This method is to get the available list of inventory items while creating a SALE.
	public ConcurrentHashMap<String,Inventory> getAvailableInventoryItems();
}