package com.assignment.seis602.sale;

import com.assignment.seis602.inventory.Inventory;
import com.assignment.seis602.item.Item;
import com.assignment.seis602.item.SaleItem;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * @Author Ryan Poorman and Aadil Sharma
 * @Description Sale keeps the state of items being processed within the register class
 */
public class Sale implements IfcSale {
    private int saleID;
    private double saleAmount;
    private Map<String, SaleItem> saleItems;

    public Sale() {
        saleID = new Random().nextInt();
        saleItems = new HashMap();
    }

    public void addItem(Item item) {
        SaleItem storedItem = saleItems.get(item.getItemName());
        if (storedItem == null) {
            saleItems.put(item.getItemName(), new SaleItem(item));
        } else {
            int count = storedItem.getItemCount();
            storedItem.setItemCount(count + 1);
            saleItems.put(item.getItemName(), storedItem);
        }

        saleAmount += item.getUnitPrice();
    }


    public void removeItem(Item item) {
        SaleItem storedItem = saleItems.get(item.getItemName());
        if (storedItem != null) {
            if (storedItem.getItemCount() == 1) {
                saleItems.remove(item.getItemName());
            } else {
                int count = storedItem.getItemCount();
                storedItem.setItemCount(count - 1 );
                saleItems.put(item.getItemName(), storedItem);
            }
        }
        saleAmount -= item.getUnitPrice();

    }


    public int getSaleID() {
        return saleID;
    }

    public double getSaleAmount() {
        return saleAmount;
    }

    public void cancelSale(Inventory inv) {
        Iterator<Map.Entry<String, SaleItem>> itr = saleItems.entrySet().iterator();
        while (itr.hasNext()) {
            Map.Entry<String, SaleItem> entry = itr.next();
            for (int i = entry.getValue().getItemCount(); i > 0; i--) {
                inv.adjustInventory(entry.getValue().getItem().getItemName(), 'A');
            }
        }
    }


    public Map getSaleItems() {
        return saleItems;
    }


}


