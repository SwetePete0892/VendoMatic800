package com.techelevator;

import com.techelevator.view.Menu;
import com.techelevator.view.VendingItem;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.Map;

import static org.junit.Assert.*;

public class VendingMachineCLITest {

    @Test
    public void vending_itemMapBuild_Tests() {
        VendingMachineCLI test = new VendingMachineCLI(new Menu(System.in, System.out));
        File tesfile = new File("vendingMachine.csv");
        Map<String, VendingItem> testMap = test.createVendingInventory(tesfile);
        assertEquals(16, testMap.size());
        assertEquals("Potato Crisps", testMap.get("A1").getName());
        assertEquals(new BigDecimal("1.80"), testMap.get("B1").getPrice());
        assertEquals("Glug Glug, Yum!", testMap.get("C1").itemMessage());
    }
}