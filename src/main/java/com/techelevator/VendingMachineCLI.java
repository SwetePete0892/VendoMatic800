package com.techelevator;

import com.techelevator.view.*;
import com.techelevator.view.VendingGUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

public class VendingMachineCLI {
	private static final String[] MAIN_MENU_OPTIONS =  MenuTextOptions.mainMenu();
	private static final String[] PURCHASE_MENU_OPTIONS = MenuTextOptions.purchaseMenu();
	private Menu menu;
	public File vendingInventoryCatalog = new File("vendingmachine.csv");
	private static Map<String, VendingItem> currentInventory = new TreeMap<>();
	private static BigDecimal currentMoney = new BigDecimal("0.00");
	private Map<String, VendingItemTypes> mapOfTypes = Map.of("Candy", VendingItemTypes.CANDY, "Chip",VendingItemTypes.CHIP,"Drink", VendingItemTypes.DRINK, "Gum", VendingItemTypes.GUM);

	// Constructor
	public VendingMachineCLI(Menu menu) {this.menu = menu;}

	// Setters & Getters

	public static void setCurrentMoney(BigDecimal updatedMoney){currentMoney = updatedMoney;}
	public static Map<String, VendingItem> getCurrentInventory() {return currentInventory;}

	public static BigDecimal getCurrentMoney(){return currentMoney;}
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
			}else if(choice.equals(MenuTextOptions.MAIN_MENU_GUI.getText())){
			}
		}
	}
	public void purchase() {
//		Scanner input = new Scanner(System.in);
		while (true) {
			System.out.println("\n**Current Money: $" + currentMoney + "**");
			String choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

			if (choice.equals(MenuTextOptions.PURCHASE_MENU_FEED_MONEY.getText())) {
				//needs the feed money method
				menu.feedMoney(currentMoney);
			} else if (choice.equals(MenuTextOptions.PURCHASE_MENU_SELECT_PRODUCT.getText())) {
				//method to select which product to buy goes here
				displayCurrentInventory(currentInventory);
				menu.selectItem(currentInventory);
			} else if (choice.equals(MenuTextOptions.PURCHASE_MENU_FINISH_TRANSACTION.getText())) {
				System.out.println(Menu.remainingChange(currentMoney));
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
				String vendingLocation = inventoryIntoParts[0];
				String nameOfItem = inventoryIntoParts[1];
				BigDecimal priceOfItem = new BigDecimal(inventoryIntoParts[2]);
				String typeOfItem = inventoryIntoParts[3];
				currentInventory.put(vendingLocation, new VendingItem(nameOfItem, priceOfItem, mapOfTypes.get(typeOfItem)));
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

		StringBuilder stringOfInventory = new StringBuilder();

		for(Map.Entry<String, VendingItem> individualVendingItem : currentInventory.entrySet()){

			// To make it easier on the eyes, all values will be placed in their own variables
			String currentLocation = individualVendingItem.getKey();
			String currentName = individualVendingItem.getValue().getName();
			BigDecimal currentPrice = individualVendingItem.getValue().getPrice();
			int currentStock = individualVendingItem.getValue().getCurrentStock();

			if(currentStock == 0){
				stringOfInventory.append(currentLocation).append(" | ").append(currentName).append(" | $").append(currentPrice).append(" | SOLD OUT\n");
				stringOfInventory.append("--------------------------------------------\n");
			} else {
				stringOfInventory.append(currentLocation).append(" | ").append(currentName).append(" | $").append(currentPrice).append(" | ").append(currentStock).append("\n");
				stringOfInventory.append("--------------------------------------------\n");

			}

		}
		return stringOfInventory.toString();
	}

}
