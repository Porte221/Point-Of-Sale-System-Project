package com.assignment.seis602.register;

import com.assignment.seis602.item.Item;

public interface IPoSRegister {

    void startOrder();

    void addItem();

    void removeItem();

    void cancelOrder();

    void checkout();

    void shutdown();
}
