package com.techelevator.view;

import java.math.BigDecimal;

public abstract class VendingItem {

    // Properties of a Vending item
    private String name;
    private BigDecimal price;
    private int currentStock = 5;

    // Constructors
    public VendingItem(){

    }

    public VendingItem(String name, BigDecimal price){
        this.name = name;
        this.price = price;
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

    public void setCurrentStock(int currentStock){
        this.currentStock = currentStock;
    }
}
