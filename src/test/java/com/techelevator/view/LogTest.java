package com.techelevator.view;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;
import static org.junit.jupiter.api.Assertions.*;

public class LogTest {

    @Test
    public void verify_theftWriter_is_writing_to_correct_file(){

        File testFile = new File("theftLogs/" + LocalDate.now().format(BASIC_ISO_DATE) + "theftLog.log");

        Log.theftLog("**Test**", BigDecimal.ZERO, BigDecimal.ONE);

        assert(testFile.exists());
    }

    @Test
    public void verify_salesWriter_is_writing_to_correct_file(){
        Map<String, VendingItem> testMap = new TreeMap<>();
        testMap.put("A1", new VendingItem("TestCandy", new BigDecimal("1.00"),  VendingItemTypes.CANDY));

        File testFile = new File("salesReportLogs/" + LocalDate.now().format(BASIC_ISO_DATE) + "salesReport.log");

        Log.salesReport(testMap);

        assert(testFile.exists());
    }

    @Test
    public void theftWriter_uses_proper_formatting() throws FileNotFoundException {
        Log.theftLog("**Test**", BigDecimal.ZERO, BigDecimal.ONE);

        File testFile = new File("theftLogs/" + LocalDate.now().format(BASIC_ISO_DATE) + "theftLog.log");
        Scanner reader = new Scanner(testFile);
        String finalLine = "";
        while(reader.hasNextLine()){
            finalLine = reader.nextLine();
        }

        String TIMESTAMP = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(LocalDateTime.now());

        String expected = TIMESTAMP + " | **Test** | $0 | $1";
        assertEquals(expected, finalLine);

    }

    @Test
    public void salesWriter_uses_proper_format() throws IOException {
        Map<String, VendingItem> testMap = new TreeMap<>();
        testMap.put("A1", new VendingItem("TestCandy", new BigDecimal("1.00"),  VendingItemTypes.CANDY));

        File testFile = new File("salesReportLogs/" + LocalDate.now().format(BASIC_ISO_DATE) + "salesReport.log");

        Log.salesReport(testMap);

        Scanner reader = new Scanner(testFile);
        List<String> fileLineArray = new ArrayList<>();
        while(reader.hasNextLine()){
            fileLineArray.add(reader.nextLine());
        }

        String expected = "TestCandy            |  0";

        assertTrue(fileLineArray.contains(expected));

    }

}