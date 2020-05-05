/*
 * author name : Aadil Sharma
 */

package com.assignment.seis602.Interfaces;

import java.util.ArrayList;

import com.assignment.seis602.Item.Item;

public interface IfcItem 
{
	//This method will generate the list of Items reading from Flat file named "Items.txt"
	//This method should be called only once during the entire application at the time of Register creation and must be 
	//called before calling generateInventoryItems() method of Inventory class.
	//or user logs in.
	public boolean StartCreateItems();
	
	//This Method will be used to create the Inventory from the returned Items
	public ArrayList<Item> getItemList();

}
