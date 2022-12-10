package com.techelevator.view;

public enum VendingItemTypes {


    CANDY("Munch Munch, Yum!"),
    GUM("Chew Chew, Yum!"),
    CHIP("Crunch Crunch, Yum!"),
    DRINK("Glug Glug, Yum!");


    final String message;



    VendingItemTypes(String message){
        this.message=message;
    }
}
