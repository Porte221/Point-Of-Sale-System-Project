package com.assignment.seis602.item;

import com.assignment.seis602.item.Item;
import com.assignment.seis602.orderStock.OrderStock;

import java.io.Serializable;

/**
 * @Author Ryan Poorman and Aadil Sharma
 * @Description InventoryItem is the POJO holding all data necessary to track and update items from the inventory
 */
public class InventoryItem implements Serializable {

    private static final long serialVersionUID = 1420672609912364060L;

    Item item;
    private OrderStock OrderItem;
    private double itemThreshold;
    private int stockPerItem;


    public InventoryItem(Item itm, double threshold, OrderStock ordrObj, int stock) {
        this.item = itm;
        this.itemThreshold = threshold;
        this.OrderItem = ordrObj;
        this.stockPerItem = stock;
    }

    public InventoryItem() {}


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getStockPerItem() {
        return stockPerItem;
    }

    public void setStockPerItem(int stockPerItem) {
        this.stockPerItem = stockPerItem;
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
}
