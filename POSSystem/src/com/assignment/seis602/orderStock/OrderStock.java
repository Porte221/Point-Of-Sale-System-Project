package com.assignment.seis602.orderStock;

import java.time.LocalDate;

import com.assignment.seis602.item.Item;

public class OrderStock 
{

	private Long orderNumber;
	private LocalDate orderDate;
	private Item orderItem;
	private String SupplierName;
	private int orderQuantity;
	private static int minOrderNumber=10;
	
	public OrderStock()
	{
	  
	}

    public OrderStock(Item ordItem,String suppName,int quantity)
    {
    	this.orderItem=ordItem;	
    	this.orderQuantity=quantity;
    	this.SupplierName=suppName;
    	
    	orderDate	= LocalDate.now();
  	    this.orderNumber=Math.round(Math.random()*5)+minOrderNumber++;
    }

	public Long getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(Long orderNumber) {
		this.orderNumber = orderNumber;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public Item getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(Item orderItem) {
		this.orderItem = orderItem;
	}

	public String getSupplierName() {
		return SupplierName;
	}

	public void setSupplierName(String supplierName) {
		SupplierName = supplierName;
	}
	
	public int getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
}

