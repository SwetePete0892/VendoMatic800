package com.techelevator.view;

public enum CoinValues {
    QUARTER(25),
    DIME(10),
    NICKEL(5);

    final int value;

    CoinValues(int value){this.value = value;}
}
