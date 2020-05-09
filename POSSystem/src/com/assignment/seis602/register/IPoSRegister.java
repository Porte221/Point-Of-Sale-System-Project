package com.assignment.seis602.register;

import com.assignment.seis602.item.Item;

public interface IPoSRegister {

    void startOrder();

    void addItem(Item itemToAdd);

    void removeItem(Item itemToRemove);

    void cancelOrder();

    void checkout();

    void shutdown();
}
