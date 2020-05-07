package com.assignment.seis602.register;

public interface IPoSRegister<T> {

    void startOrder();

    void addItem(T itemToAdd);

    void removeItem(T itemToRemove);

    void cancelOrder();

    void checkout();

    void shutdown();
}
