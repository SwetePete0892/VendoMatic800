package com.techelevator.view;

import com.techelevator.VendingMachineCLI;

import javax.swing.*;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Menu {

	private PrintWriter out;
	private Scanner in;

	private final int MAX_MENU_SIZE = 3;

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if (choice == null) {
			out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < MAX_MENU_SIZE; i++) {

			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print(System.lineSeparator() + "Please choose an option >>> ");
		out.flush();
	}

	// Make Change Method

	public void feedMoney(BigDecimal currentMoney) {
		//needs the feed money method
		// checks that is not equal to zero and greater than 0
		//if it is 0 or neg number will say they can't input such number
		//if user does not want to enter more money will display money entered and current money

			System.out.println("How much will be inserted?");
			try {
				Integer amount = Integer.valueOf(in.nextLine());
				if (amount > 0) {
					currentMoney = currentMoney.add(new BigDecimal(amount + ".00"));
					VendingMachineCLI.setCurrentMoney(currentMoney);
					Log.theftLog("Feed Money", new BigDecimal(amount + ".00"), VendingMachineCLI.getCurrentMoney());

				} else {
					System.out.println("cant input a negative number or 0");
				}
				System.out.println("**Amount Entered: $" + amount + ".00**");
			} catch(NumberFormatException nfe){
				System.out.println("Please enter valid input");
			}
	}

	public static String remainingChange(BigDecimal change){
		Integer tempChange = change.multiply(new BigDecimal(100)).intValue();
		int quarters = tempChange / 25;
		tempChange = tempChange % 25;
		int dimes = tempChange / 10;
		tempChange = tempChange % 10;
		int nickels = tempChange / 5;

		String givenChange =("Your change of $" + VendingMachineCLI.getCurrentMoney() + ": " + quarters +  " quarters "
				+ dimes + " dimes " + nickels + " nickels ");

		VendingMachineCLI.setCurrentMoney(BigDecimal.ZERO);
		Log.theftLog("Give Change", change, VendingMachineCLI.getCurrentMoney());
		return givenChange;
	}
	public void selectItem(Map<String, VendingItem> map){

				System.out.println("Please Type the items code");
				String code = in.nextLine().toUpperCase();
				Menu.buyItem(code, map);

	}
	public static void buyItem(String code, Map<String, VendingItem> map ){
		//first checks if code is in map
		// checks if machine has enough money first
		// if it is sold out return to menu
		// if not Sold out print item's description and message and reduce stock
		//if code is not in map item not found and returns to menu
		if(map.containsKey(code)){
			if(VendingMachineCLI.getCurrentMoney().compareTo(map.get(code).getPrice()) < 0){
				System.out.println("There's not enough money"+" current money: $"+VendingMachineCLI.getCurrentMoney());
			}else if(map.get(code).getCurrentStock() == 0){
				System.out.println("The current Item is SOLD OUT");
			}else{
				map.get(code).reduceStock();
				VendingMachineCLI.setCurrentMoney(VendingMachineCLI.getCurrentMoney().subtract(map.get(code).getPrice()));
				Log.theftLog(map.get(code).getName() + " " + code, map.get(code).getPrice(), VendingMachineCLI.getCurrentMoney());
				System.out.println("\n" + map.get(code).getName()+" cost: $"+map.get(code).getPrice()+" Money Remaining: $"+VendingMachineCLI.getCurrentMoney()+" " +
						"\n"+map.get(code).itemMessage());
			}
		}else {
			System.out.println("Item not found");
		}
	}



	public static void feedMoneyGUI(int amount, JFrame window){
		if(amount>0){
			VendingMachineCLI.setCurrentMoney(new BigDecimal(amount).add(VendingMachineCLI.getCurrentMoney()));
			Log.theftLog("Feed Money", new BigDecimal(amount), VendingMachineCLI.getCurrentMoney());
		}
		else JOptionPane.showMessageDialog(window, "Please enter a valid number", "Invalid Input", JOptionPane.ERROR_MESSAGE);
	}
	public static void selectItemGUI(String item, Map<String, VendingItem> inventory, JFrame window){
		item = item.toUpperCase();


			if (inventory.containsKey(item) ) {
				if (VendingMachineCLI.getCurrentMoney().compareTo(inventory.get(item).getPrice()) < 0) JOptionPane.showMessageDialog(window, "Not enough funds, please insert more money", "Inadequate Funds", JOptionPane.ERROR_MESSAGE);
				else if (inventory.get(item).getCurrentStock() < 1) JOptionPane.showMessageDialog(window, "Item Unavailable, please try again", "SOLD OUT", JOptionPane.ERROR_MESSAGE);
				else{VendingMachineCLI.setCurrentMoney(VendingMachineCLI.getCurrentMoney().subtract(inventory.get(item).getPrice()));
				inventory.get(item).reduceStock();
				inventory.get(item).itemMessage();
					System.out.println("Current Money: $"+VendingMachineCLI.getCurrentMoney());
				JOptionPane.showMessageDialog(window, "Dispensing : "+inventory.get(item).getName() + "\n Price: "+ inventory.get(item).getPrice(), inventory.get(item).itemMessage(), JOptionPane.INFORMATION_MESSAGE);
				Log.theftLog(inventory.get(item).getName()+" "+item, inventory.get(item).getPrice(), VendingMachineCLI.getCurrentMoney());
				}
			} else JOptionPane.showMessageDialog(window, "Please enter valid Vending Location", "Invalid Input", JOptionPane.ERROR_MESSAGE);



	}
}
