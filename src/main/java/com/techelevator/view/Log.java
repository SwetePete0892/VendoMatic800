package com.techelevator.view;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Map;

public class Log {

    private static PrintWriter theftWriter;
    private static final String TIMESTAMP = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(LocalDateTime.now());

    public static void theftLog(String action, BigDecimal transactionAmount, BigDecimal totalMoney) {
        // Trying to reduce how many times we need to open and close a PrintWriter
            // With the naming convention, I hope to build a folder that holds all the theft logs.
            // It creates a new log for every day. If a log for the current day exists, it simply appends to it.

        try{
            if(theftWriter == null){
                theftWriter = new PrintWriter(new FileOutputStream(LocalDate.now() + "theftLog.log",true), true);
            }

            //I need to go back and learn about local currency and how to use that
            theftWriter.println(TIMESTAMP + " " + action + " $" + transactionAmount + " $" + totalMoney);

        } catch (FileNotFoundException nfe){
            System.out.println(nfe.getMessage());

        } catch (Exception e) {
            // Should I make a custom exception?
            System.out.println("There was a problem with writing to theftLog");

        }
    }

    // This was made with the intent of being run once at the end of the program.
    // I think it works for the scope of the project because we never exceed 5 or restock
    // I plan to think of a better method that can handle restocking.
    public static void salesReport(Map<String, VendingItem> remainingInventory){
        try(PrintWriter salesWriter = new PrintWriter(new FileOutputStream(LocalDate.now() + "salesReport.txt", true))) {
            BigDecimal totalSales = new BigDecimal("0.00");

            salesWriter.println("SALES REPORT: " + TIMESTAMP);

            for (Map.Entry<String, VendingItem> individualUnit : remainingInventory.entrySet()) {
                // For the sanity of the reader, values are put into variables
                String itemName = individualUnit.getValue().getName();
                BigDecimal itemPrice = individualUnit.getValue().getPrice();
                int currentStock = individualUnit.getValue().getCurrentStock();

                if (currentStock < 5) {
                    int unitsSold = 5 - currentStock;
                    BigDecimal unitsSoldDecimal = new BigDecimal(unitsSold);
                    totalSales = totalSales.add(itemPrice.multiply(unitsSoldDecimal));
                    System.out.println(itemName + " | " + unitsSold);
                    salesWriter.println(itemName + " | " + unitsSold);

                } else {
                    System.out.println(itemName + " | " + 0);
                    salesWriter.println(itemName + " | " + 0);
                }
            }
            System.out.println("**TOTAL SALES** " + totalSales);
            salesWriter.println("**TOTAL SALES** " + totalSales + "\n");

        } catch (Exception e) {
            System.out.println("Something went wrong with the Sales Report");
        }
    }


}
