package com.assignment.seis602.orderStock;

import java.time.LocalDate;

import com.assignment.seis602.item.Item;

public class OrderStock 
{

	private int orderNumber;
	private LocalDate orderDate;
	private Item orderItem;
	private String SupplierName;
	private String SupplierAddress;
	private int orderQuantity;
	
	OrderStock()
	{
	  	
	}

    OrderStock(int ordrNum,LocalDate ordDate,Item ordItem,String suppName, String suppAddr,int quantity)
    {
    	this.orderDate=ordDate;
    	this.orderItem=ordItem;
    	this.orderNumber=ordrNum;
    	this.orderQuantity=quantity;
    	this.SupplierAddress=suppAddr;
    	this.SupplierName=suppName;
    }

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
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

	public String getSupplierAddress() {
		return SupplierAddress;
	}

	public void setSupplierAddress(String supplierAddress) {
		SupplierAddress = supplierAddress;
	}

	public int getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}
}

