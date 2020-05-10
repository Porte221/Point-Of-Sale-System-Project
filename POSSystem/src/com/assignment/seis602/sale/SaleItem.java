package com.assignment.seis602.sale;

import com.assignment.seis602.item.Item;

public class SaleItem {
    Item item;
    int itemCount;

    public SaleItem(Item i) {
        setItem(i);
        setItemCount(1);
    }

    public SaleItem(Item i, int count) {
        setItem(i);
        setItemCount(count);
    }


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

}
