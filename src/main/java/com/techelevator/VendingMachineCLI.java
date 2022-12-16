package com.techelevator;

import com.techelevator.view.*;
import com.techelevator.view.VendingGUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class VendingMachineCLI {
	private static final String[] MAIN_MENU_OPTIONS =  MenuTextOptions.mainMenu();
	private static final String[] PURCHASE_MENU_OPTIONS = MenuTextOptions.purchaseMenu();
	private Menu menu;
	public File vendingInventoryCatalog = new File("vendingmachine.csv");
	private static Map<String, VendingItem> currentInventory = new TreeMap<>();
	private static BigDecimal currentMoney = new BigDecimal("0.00");

	// Constructor
	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	// Setters & Getters

	public static void setCurrentMoney(BigDecimal updatedMoney){currentMoney = updatedMoney;}
	public static Map<String, VendingItem> getCurrentInventory() {return currentInventory;}

	public static BigDecimal getCurrentMoney(){
		return currentMoney;
	}
	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.createVendingInventory();
		new VendingGUI();
	}
	public static void alternateRun(){
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}



	public void run() {

		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MenuTextOptions.MAIN_MENU_DISPLAY_ITEMS.getText())) {
				// display vending machine items
				displayCurrentInventory(currentInventory);

			} else if (choice.equals(MenuTextOptions.MAIN_MENU_OPTION_PURCHASE.getText())) {
				// do purchase
				purchase();
			} else if (choice.equals(MenuTextOptions.MAIN_MENU_OPTION_EXIT.getText())) {
				// exit program
				System.out.println("\nThanks for using Vendo-Matic 800, an Umbrella Corp. project.");
				System.exit(0);
			} else if (choice.equals(MenuTextOptions.MAIN_MENU_DISPLAY_SECRET.getText())){
				Log.salesReport(currentInventory);
			}
		}
	}
	public void purchase() {
//		Scanner input = new Scanner(System.in);
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

			if (choice.equals(MenuTextOptions.PURCHASE_MENU_FEED_MONEY.getText())) {
				//needs the feed money method
				menu.feedMoney(currentMoney);
			} else if (choice.equals(MenuTextOptions.PURCHASE_MENU_SELECT_PRODUCT.getText())) {
				//method to select which product to buy goes here
				displayCurrentInventory(currentInventory);
				menu.selectItem(currentInventory);
			} else if (choice.equals(MenuTextOptions.PURCHASE_MENU_FINISH_TRANSACTION.getText())) {
				Menu.remainingChange(currentMoney);
				break;
			}
		}
	}


	//modified slightly****************************************************************************************
	public void createVendingInventory(){
		// Each line of the file will be split into an array.
			// Each part of the array will be used to create an item and then store that item in a Map.
		try(Scanner dataInput = new Scanner(vendingInventoryCatalog)){
			while(dataInput.hasNextLine()){
				String currentInventoryItem = dataInput.nextLine();
				String[] inventoryIntoParts = currentInventoryItem.split("\\|");

				// For readability, I will store each item of the array in a variable
					// Turning a string into a BigDecimal is better for accuracy and format, so I have found
				String vendingLocation = inventoryIntoParts[0];
				String nameOfItem = inventoryIntoParts[1];
				BigDecimal priceOfItem = new BigDecimal(inventoryIntoParts[2]);
				String typeOfItem = inventoryIntoParts[3];

				// Using a switch case to identify the type of item and then add to our Map
				//Modified the item creation to use ENUM instead for item types*************************************
				switch(typeOfItem.toLowerCase()){
					case "gum":
						VendingItem gumItem = new VendingItem(nameOfItem, priceOfItem, VendingItemTypes.GUM);
						currentInventory.put(vendingLocation, gumItem);
						break;

					case "chip":
						VendingItem chipItem = new VendingItem(nameOfItem, priceOfItem, VendingItemTypes.CHIP);
						currentInventory.put(vendingLocation, chipItem);
						break;

					case "drink":
						VendingItem drinkItem = new VendingItem(nameOfItem, priceOfItem, VendingItemTypes.DRINK);
						currentInventory.put(vendingLocation, drinkItem);
						break;

					case "candy":
						VendingItem candyItem = new VendingItem(nameOfItem, priceOfItem, VendingItemTypes.CANDY);
						currentInventory.put(vendingLocation, candyItem);
						break;

					default:
						System.out.println("Something went wrong while trying to create an item from the file.");
				}

			}

		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
		}

	}

	public void displayCurrentInventory(Map<String, VendingItem> currentInventory){
		System.out.println("--------------------------------------------");

		for(Map.Entry<String, VendingItem> individualVendingItem : currentInventory.entrySet()){

			// To make it easier on the eyes, all values will be placed in their own variables
			String currentLocation = individualVendingItem.getKey();
			String currentName = individualVendingItem.getValue().getName();
			BigDecimal currentPrice = individualVendingItem.getValue().getPrice();
			int currentStock = individualVendingItem.getValue().getCurrentStock();

			if(currentStock == 0){
				System.out.printf(currentLocation + " | %-20s | %-4s | SOLD OUT\n", currentName, currentPrice);
				System.out.println("--------------------------------------------");
			} else {
				System.out.printf(currentLocation + " | %-20s | %-4s | %5d\n", currentName, currentPrice, currentStock);
				System.out.println("--------------------------------------------");

			}

		}
	}
	public static String displayCurrentInventoryString(Map<String, VendingItem> currentInventory){

		StringBuilder test = new StringBuilder();

		for(Map.Entry<String, VendingItem> individualVendingItem : currentInventory.entrySet()){

			// To make it easier on the eyes, all values will be placed in their own variables
			String currentLocation = individualVendingItem.getKey();
			String currentName = individualVendingItem.getValue().getName();
			BigDecimal currentPrice = individualVendingItem.getValue().getPrice();
			int currentStock = individualVendingItem.getValue().getCurrentStock();

			if(currentStock == 0){
				test.append(currentLocation).append(" | ").append(currentName).append(" | ").append(currentPrice).append(" | SOLD OUT\n");
				test.append("--------------------------------------------\n");
			} else {
				test.append(currentLocation).append(" | ").append(currentName).append(" | ").append(currentPrice).append(" | ").append(currentStock).append("\n");
				test.append("--------------------------------------------\n");

			}

		}
		return test.toString();
	}

}
