package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Map;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

public class Log {

    // Properties
    private static final File THEFT_REPORT_DIRECTORY = new File("theftLogs/");
    private static final File SALES_REPORT_DIRECTORY = new File("salesReportLogs/");
    private static PrintWriter theftWriter;
    private static PrintWriter salesReportWriter;
    private static final String TIMESTAMP = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(LocalDateTime.now());
    private static final String DATESTAMP = LocalDate.now().format(BASIC_ISO_DATE);

    /* Logs instances of transactions: feedMoney, purchaseItem, makeChange. Creates a Printwriter on initial use.
     * Creates a directory to hold the reports if one does not exist. New files are created daily.
     * If a file exists for the day, appends to that file. All transactions are timestamped. */

    public static void theftLog(String action, BigDecimal transactionAmount, BigDecimal totalMoney) {

        try{

            if(THEFT_REPORT_DIRECTORY.mkdir()){
                // Creates directory for theft reports if it doesn't exist.
            }

            if(theftWriter == null){
                theftWriter = new PrintWriter(new FileOutputStream(
                        THEFT_REPORT_DIRECTORY + "/" + DATESTAMP + "theftLog.log",true), true);
            }

            //I need to go back and learn about local currency and how to use that
            theftWriter.println(TIMESTAMP + " | " + action + " | $" + transactionAmount + " | $" + totalMoney);

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

        try {

            if(SALES_REPORT_DIRECTORY.mkdir()){
                // Creates sales report directory if it doesn't exist.
            }

            if(salesReportWriter == null){
                salesReportWriter = new PrintWriter(new FileOutputStream(
                        SALES_REPORT_DIRECTORY + "/" + DATESTAMP + "salesReport.log",true), true);
            }

            BigDecimal totalSales = new BigDecimal("0.00");

            salesReportWriter.println("SALES REPORT: " + TIMESTAMP);
            salesReportWriter.println("---------------------------");

            for (Map.Entry<String, VendingItem> individualUnit : remainingInventory.entrySet()) {
                // For the sanity of the reader, values are put into variables
                String itemName = individualUnit.getValue().getName();
                BigDecimal itemPrice = individualUnit.getValue().getPrice();
                int currentStock = individualUnit.getValue().getCurrentStock();

                if (currentStock < VendingItem.getMAX_STOCK_LIMIT()) {
                    int unitsSold = VendingItem.getMAX_STOCK_LIMIT() - currentStock;
                    BigDecimal unitsSoldDecimal = new BigDecimal(unitsSold);
                    totalSales = totalSales.add(itemPrice.multiply(unitsSoldDecimal));
                    salesReportWriter.printf("%-20s | %2d\n", itemName, unitsSold);
                    salesReportWriter.println("---------------------------");

                } else {
                    salesReportWriter.printf("%-20s | %2d\n", itemName, 0);
                    salesReportWriter.println("---------------------------");
                }
            }
            salesReportWriter.println(" **TOTAL SALES** $" + totalSales + "\n");
            System.out.println("\n**Generated sales report**");

        } catch (Exception e) {
            System.out.println("Something went wrong with the Sales Report");
        }
    }


}