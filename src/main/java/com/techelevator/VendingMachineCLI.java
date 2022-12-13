package com.techelevator;

import com.techelevator.view.*;

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
	
	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}
	public File vendingInventoryCatalog = new File("vendingmachine.csv");
	Map<String, VendingItem> currentInventory = new TreeMap<>(createVendingInventory(vendingInventoryCatalog));

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
				System.out.println("Thanks for using Vendo-Matic 800, an Umbrella Corp. project.");
				break;
			}
		}
	}
	public void purchase() {

		while (true) {
			String choice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

			if (choice.equals(MenuTextOptions.PURCHASE_MENU_FEED_MONEY.getText())) {
				//needs the feed money method

			} else if (choice.equals(MenuTextOptions.PURCHASE_MENU_SELECT_PRODUCT.getText())) {
				//method to select which product to buy goes here

			} else if (choice.equals(MenuTextOptions.PURCHASE_MENU_FINISH_TRANSACTION.getText())) {
				//Needs to call the method that will give out all the change
				break;
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
	//modified slightly****************************************************************************************
	public Map<String, VendingItem> createVendingInventory(File fileWithInventory){
		// Creating a Tree map to hold our new items and keep them in order.
			// The keys will be the locations
		Map<String, VendingItem> vendingInventory = new TreeMap<>();

		// Each line of the file will be split into an array.
			// Each part of the array will be used to create an item and then store that item in a Map.
		try(Scanner dataInput = new Scanner(fileWithInventory)){
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
						vendingInventory.put(vendingLocation, gumItem);
						break;

					case "chip":
						VendingItem chipItem = new VendingItem(nameOfItem, priceOfItem, VendingItemTypes.CHIP);
						vendingInventory.put(vendingLocation, chipItem);
						break;

					case "drink":
						VendingItem drinkItem = new VendingItem(nameOfItem, priceOfItem, VendingItemTypes.DRINK);
						vendingInventory.put(vendingLocation, drinkItem);
						break;

					case "candy":
						VendingItem candyItem = new VendingItem(nameOfItem, priceOfItem, VendingItemTypes.CANDY);
						vendingInventory.put(vendingLocation, candyItem);
						break;

					default:
						System.out.println("Something went wrong while trying to create an item from the file.");
				}

			}

		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
		}
		return vendingInventory;

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

}
