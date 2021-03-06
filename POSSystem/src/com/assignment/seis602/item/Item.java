package com.assignment.seis602.item;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * @Author Aadil Sharma
 * @Description Item Class represents the Items for which inventory will be generated.
 *              It follows a singleton pattern to initialize the item objects at the start of application.
 */

public class Item implements Serializable, IfcItem {

    private String itemName;
    private ItemCategory itemCategory;
    private double unitPrice;
    private final static ArrayList<Item> ItemList = new ArrayList<Item>();

    public Item() {

    }

    public Item(String name, ItemCategory category, double price) {
        this.itemCategory = category;
        this.itemName = name;
        this.unitPrice = price;
    }

    public static ArrayList<Item> getItemList() {
        return ItemList;
    }

    public boolean StartCreateItems() {
        return createItems();
    }

    //This Methods are to be called only once at the start of the application when user log in is successfully.
    //Create Items from resource files
    private boolean createItems() {
        //Read data from flat File , Create Items based it this data , Add created items to the ArrayList.
        Scanner scr = null;
        File sourceFile;

        try {
            sourceFile = new File("resources/InitializationFiles/Items.txt");
            scr = new Scanner(sourceFile);
            while (scr.hasNextLine()) {
                String readLine = scr.nextLine();
                String[] Str = readLine.split(",");
                ItemCategory itemCat = ItemCategory.Miscellaneous;

                if (Str.length == 3) {
                    switch (Str[1]) {
                        case "E":
                            itemCat = ItemCategory.Electronics;
                            break;
                        case "H":
                            itemCat = ItemCategory.Household;
                            break;
                        case "HF":
                            itemCat = ItemCategory.HomeFurnishing;
                            break;
                        case "G":
                            itemCat = ItemCategory.Grocery;
                            break;
                        default:
                            itemCat = ItemCategory.Miscellaneous;
                            break;
                    }

                    ItemList.add(new Item(Str[0], itemCat, Double.parseDouble(Str[2])));
                }
            }
            return true;
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            scr.close();
        }
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String toString() {
        return this.getItemName() + ", " + this.getItemCategory() + ", " + this.unitPrice;
    }
}
