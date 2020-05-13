package com.assignment.seis602.register;

import com.assignment.seis602.item.Item;

/**
 * @Author Ryan Poorman
 * @Description PoSRegIPoSRegister creates the framework by which the register class must adhere. It was determined that these are the basic
 * activites that would be done in any general register
 */
public interface IPoSRegister {

    void startOrder();

    void addItem();

    void removeItem();

    void cancelOrder();

    void checkout();

    void shutdown();

    void startup();

    void removePreviousSale();
}
