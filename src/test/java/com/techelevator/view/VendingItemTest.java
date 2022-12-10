package com.techelevator.view;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class VendingItemTest {

    @Test
    public void testing_for_item_Construction_and_messages() {
        VendingItem testItem = new VendingItem("generic chips", BigDecimal.TEN, VendingItemTypes.CHIP);
        assertEquals("generic chips", testItem.getName());
        assertEquals(BigDecimal.TEN, testItem.getPrice());
        assertEquals("Crunch Crunch, Yum!", testItem.itemMessage());

        testItem = new VendingItem("generic chips", new BigDecimal(240), VendingItemTypes.CANDY);
        assertEquals("generic chips", testItem.getName());
        assertEquals(BigDecimal.valueOf(240), testItem.getPrice());
        assertEquals("Munch Munch, Yum!", testItem.itemMessage());

        testItem = new VendingItem("generic chips", BigDecimal.ZERO, VendingItemTypes.DRINK);
        assertEquals("generic chips", testItem.getName());
        assertEquals(BigDecimal.ZERO, testItem.getPrice());
        assertEquals("Glug Glug, Yum!", testItem.itemMessage());

        testItem = new VendingItem("generic chips", new BigDecimal("25.5"), VendingItemTypes.GUM);
        assertEquals("generic chips", testItem.getName());
        assertEquals(BigDecimal.valueOf(25.5), testItem.getPrice());
        assertEquals("Chew Chew, Yum!", testItem.itemMessage());
        testItem.reduceStock();
        assertEquals(4, testItem.getCurrentStock());

    }
}