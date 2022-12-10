package com.techelevator.view;

import java.math.BigDecimal;

public class VendingItem {

    // Properties of a Vending item
    private String name;
    private BigDecimal price;
    private int currentStock = 5;

    private VendingItemTypes itemType;

    // Constructors
    public VendingItem(){

    }

    public VendingItem(String name, BigDecimal price, VendingItemTypes itemType){
        this.name = name;
        this.price = price;
        this.itemType = itemType;
    }

    // Getters & Setters


    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void reduceStock(){
        if(currentStock>0)currentStock--;
    }

    public String itemMessage(){
        return itemType.message;
    }
}

