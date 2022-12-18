package com.techelevator;

import com.techelevator.view.Menu;
import com.techelevator.view.VendingItem;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.Map;

import static org.junit.Assert.*;

public class VendingMachineCLITest {
    @Before
            public void buildForTests() {
        VendingMachineCLI test = new VendingMachineCLI(new Menu(System.in, System.out));
        test.createVendingInventory();
    }

    @Test
    public void item_Total_build_Test() {
        assertEquals(16, VendingMachineCLI.getCurrentInventory().size());

    }
    @Test
    public void checking_Item_Prices(){
        assertEquals(new BigDecimal("1.80"), VendingMachineCLI.getCurrentInventory().get("B1").getPrice());
        assertEquals(new BigDecimal("1.50"), VendingMachineCLI.getCurrentInventory().get("C2").getPrice());
        assertEquals(new BigDecimal(".85"), VendingMachineCLI.getCurrentInventory().get("D1").getPrice());

    }
    @Test
    public void checking_Item_Names(){
        assertEquals("Potato Crisps", VendingMachineCLI.getCurrentInventory().get("A1").getName());
        assertEquals("Wonka Bar", VendingMachineCLI.getCurrentInventory().get("B3").getName());
        assertEquals("Triplemint", VendingMachineCLI.getCurrentInventory().get("D4").getName());

    }
}