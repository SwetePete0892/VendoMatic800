package com.techelevator.view;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.techelevator.VendingMachineCLI;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import com.techelevator.view.Menu;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MenuTest {

	private ByteArrayOutputStream output;

	@Before
	public void setup() {
		output = new ByteArrayOutputStream();
	}

	@Test
	public void displays_a_list_of_menu_options_and_prompts_user_to_make_a_choice() {
		Object[] options = new Object[] { Integer.valueOf(3), "Blind", "Mice" };
		Menu menu = getMenuForTesting();

		menu.getChoiceFromOptions(options);

		String expected = System.lineSeparator() + "1) " + options[0].toString() + System.lineSeparator() + "2) " + options[1].toString() + System.lineSeparator() + "3) "
				+ options[2].toString() + System.lineSeparator() + System.lineSeparator() + "Please choose an option >>> ";
		Assert.assertEquals(expected, output.toString());
	}

	@Test
	public void returns_object_corresponding_to_user_choice() {
		Integer expected = Integer.valueOf(456);
		Integer[] options = new Integer[] { Integer.valueOf(123), expected, Integer.valueOf(789) };
		Menu menu = getMenuForTestingWithUserInput("2" + System.lineSeparator());

		Integer result = (Integer) menu.getChoiceFromOptions(options);

		Assert.assertEquals(expected, result);
	}

	@Test
	public void redisplays_menu_if_user_does_not_choose_valid_option() {
		Object[] options = new Object[] { "Larry", "Curly", "Moe" };
		Menu menu = getMenuForTestingWithUserInput("4" + System.lineSeparator() + "1" + System.lineSeparator());

		menu.getChoiceFromOptions(options);

		String menuDisplay = System.lineSeparator() + "1) " + options[0].toString() + System.lineSeparator() + "2) " + options[1].toString() + System.lineSeparator() + "3) "
				+ options[2].toString() + System.lineSeparator() + System.lineSeparator() + "Please choose an option >>> ";

		String expected = menuDisplay + System.lineSeparator() + "*** 4 is not a valid option ***" + System.lineSeparator() + System.lineSeparator() + menuDisplay;

		Assert.assertEquals(expected, output.toString());
	}

	@Test
	public void redisplays_menu_if_user_chooses_option_less_than_1() {
		Object[] options = new Object[] { "Larry", "Curly", "Moe" };
		Menu menu = getMenuForTestingWithUserInput("0" + System.lineSeparator() + "1" + System.lineSeparator());

		menu.getChoiceFromOptions(options);

		String menuDisplay = System.lineSeparator() + "1) " + options[0].toString() + System.lineSeparator() + "2) " + options[1].toString() + System.lineSeparator() + "3) "
				+ options[2].toString() + System.lineSeparator() + System.lineSeparator() + "Please choose an option >>> ";

		String expected = menuDisplay + System.lineSeparator() + "*** 0 is not a valid option ***" + System.lineSeparator() + System.lineSeparator() + menuDisplay;

		Assert.assertEquals(expected, output.toString());
	}

	@Test
	public void redisplays_menu_if_user_enters_garbage() {
		Object[] options = new Object[] { "Larry", "Curly", "Moe" };
		Menu menu = getMenuForTestingWithUserInput("Mickey Mouse" + System.lineSeparator() + "1" + System.lineSeparator());

		menu.getChoiceFromOptions(options);

		String menuDisplay = System.lineSeparator() + "1) " + options[0].toString() + System.lineSeparator() + "2) " + options[1].toString() + System.lineSeparator() + "3) "
				+ options[2].toString() + System.lineSeparator() + System.lineSeparator() + "Please choose an option >>> ";

		String expected = menuDisplay + System.lineSeparator() + "*** Mickey Mouse is not a valid option ***" + System.lineSeparator() + System.lineSeparator() + menuDisplay;

		Assert.assertEquals(expected, output.toString());
	}
	@Test
	public void testing_Remaining_Change(){
		String test = Menu.remainingChange(new BigDecimal("2.05"));
		VendingMachineCLI.setCurrentMoney(BigDecimal.ZERO);
		Assert.assertEquals((" 8 quarters 0 dimes 1 nickels "), test.substring(test.length()-30));
		test = Menu.remainingChange(new BigDecimal("0.85"));
		Assert.assertEquals((" 3 quarters 1 dimes 0 nickels "), test.substring(test.length()-30));
		test = Menu.remainingChange(new BigDecimal("100.00"));
		Assert.assertEquals((" 400 quarters 0 dimes 0 nickels "), test.substring(test.length()-32));

	}
	@Test
	public void testing_Buy_Item(){
		Map<String, VendingItem> testMap = new HashMap<>();
		testMap.put("A1", new VendingItem("Chip", new BigDecimal("2.05"), VendingItemTypes.CHIP));
		testMap.put("B2", new VendingItem("Some kinda candy", new BigDecimal("0.95"), VendingItemTypes.CANDY));
		VendingMachineCLI.setCurrentMoney(new BigDecimal(100));
		Menu.buyItem("A1", testMap);
		Assert.assertEquals(4, testMap.get("A1").getCurrentStock());
		Menu.buyItem("A1", testMap);
		Menu.buyItem("A1", testMap);
		Menu.buyItem("A1", testMap);
		Menu.buyItem("A1", testMap);
		Assert.assertEquals(0, testMap.get("A1").getCurrentStock());
	}

	private Menu getMenuForTestingWithUserInput(String userInput) {
		ByteArrayInputStream input = new ByteArrayInputStream(String.valueOf(userInput).getBytes());
		return new Menu(input, output);
	}

	private Menu getMenuForTesting() {
		return getMenuForTestingWithUserInput("1" + System.lineSeparator());
	}
}
