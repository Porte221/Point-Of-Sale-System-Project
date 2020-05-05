/*
 * author name : Aadil Sharma
 */


package com.assignment.seis602.Interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import com.assignment.seis602.Sale.*;
import com.assignment.seis602.Inventory.*;
import com.assignment.seis602.Item.*;

public interface IfcSale 
{
	//Pass the same inventory object which was used to initialize inventory when register started.
	//First Call getAvailableInventoryItems() method of Inventory Interface to fetch the list of all available items in the Inventory
	//Choose items for the sale and pass it on as a List 
	public boolean createSale(Inventory Ivr,ArrayList<Item> ItemList);	
	
	/*
	 *Following methods are yet to be completely implemented in code and 
	 *Implementation may change based on the final code
	*/
	
	//This method will be used to get the Items linked to a Sale.
	public HashMap<Integer,Sale> getItemsForSaleMap(int saleID);
	
	
	//This method will check if the sale id being passed by User is valid and return a boolean.
	public boolean isSaleIDValid(int SaleID);
	
	
	//If this method returns true , then Register class should update the Register Object.
	//Before making CancelSale() call ,Check the validity of SaleID by calling isSaleIDValid method and if true pass the sale ID
	public boolean cancelSale(int saleId);
		
	
	//Check the validity of SaleID by calling isSaleIDValid method and if true pass the sale ID
	//Choose the items to be selected by first calling the getItemsforSale() method, pass these items to this method
    public boolean ReturnItemOnSale(int SaleId,ArrayList<Item> ItemList);
}