/*
 * author name : Aadil Sharma
 */


package com.assignment.seis602.sale;

import java.util.ArrayList;
import java.util.HashMap;

import com.assignment.seis602.inventory.*;
import com.assignment.seis602.item.*;

public interface IfcSale
{

    public boolean returnItemOnSale(int SaleId);

    public void addItem(Item item);

    public void removeItem(Item item);
}
