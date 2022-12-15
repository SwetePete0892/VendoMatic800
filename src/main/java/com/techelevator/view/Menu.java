package com.techelevator.view;

import com.techelevator.VendingMachineCLI;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

	private PrintWriter out;
	private Scanner in;

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
		for (int i = 0; i < options.length; i++) {

			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print(System.lineSeparator() + "Please choose an option >>> ");
		out.flush();
	}

	// Make Change Method

	public void feedMoney(BigDecimal currentMoney) {
		//needs the feed money method
		// will continue to asking if wanting to input money if so checks that is not equal to zero and greater than 0
		//if it is 0 or neg number will say they can't input such number
		//if user does not want to enter more money will display money entered and current money

			System.out.println("How much will be inserted?");
			try {
				Integer amount = Integer.valueOf(in.nextLine());
				if (amount > 0) {
					currentMoney = currentMoney.add(new BigDecimal(amount));
					VendingMachineCLI.setCurrentMoney(currentMoney);
					Log.theftLog("Feed Money", new BigDecimal(amount), VendingMachineCLI.getCurrentMoney());

				} else {
					System.out.println("cant input a negative number or 0");
				}
				System.out.println("Amount Entered: $" + amount + ".00 Current Amount: $" + currentMoney);
			} catch(NumberFormatException nfe){
				System.out.println("Please enter valid input");
				System.out.println("\nCurrent Amount: $" + currentMoney);
			}
	}

	public void remainingChange(BigDecimal change){
		Integer tempChange = change.multiply(new BigDecimal(100)).intValue();
		int quarters = tempChange / 25;
		tempChange = tempChange % 25;
		int dimes = tempChange / 10;
		tempChange = tempChange % 10;
		int nickels = tempChange / 5;
		VendingMachineCLI.setCurrentMoney(BigDecimal.ZERO);


		System.out.println("Your change: " + quarters +  " quarters "
				+ dimes + " dimes " + nickels + " nickels ");
	}
}
