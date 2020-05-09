/*
 * author name : Aadil Sharma
 */

package com.assignment.seis602.item;

import java.util.ArrayList;

import com.assignment.seis602.item.Item;

public interface IfcItem 
{
	//This Method will be used to create the inventory from the returned Items
	public static ArrayList<Item> getItemList(){return Item.getItemList();};

}
