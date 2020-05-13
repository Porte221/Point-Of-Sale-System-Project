package com.assignment.seis602.orderStock;

import java.io.Serializable;
import java.time.LocalDate;
import com.assignment.seis602.item.Item;

public class OrderStock implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6206518375854583319L;
	private int orderNumber;
	private String orderDate;
	private Item orderItem;
	private String supplierName;
	private int orderQuantity;
	private static int minOrderNumber=10;
	
	public OrderStock()
	{
	  setSupplierName("Undetermined");
	}

    public OrderStock(Item ordItem,String suppName,int quantity)
    {
    	this.orderItem=ordItem;	
    	this.orderQuantity=quantity;
    	this.supplierName =suppName;
    	this.orderDate= LocalDate.now()+","+java.time.ZonedDateTime.now().getHour()+":"+java.time.ZonedDateTime.now().getMinute()+":"+java.time.ZonedDateTime.now().getSecond();
  	    this.orderNumber=(minOrderNumber++)*500;
    }

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public Item getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(Item orderItem) {
		this.orderItem = orderItem;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	public int getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
	
	public String toString()
	{
		return this.getOrderNumber()+" || "+this.getOrderDate()+" || "+this.getOrderQuantity()+" || "+this.getSupplierName()+" || "+this.getOrderItem().getItemName();
	}
}

