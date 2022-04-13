package com.bank;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bank {
	BankService bankService = new BankServiceImpl();

	Map<String, Account> accounts = new HashMap<String, Account>();
	String loggedIn = "";

	public static void main(String[] args) {
		System.out.println(""
				+ "| -------------------------------------------------------------------------------------------------------------------------------- | \n"
				+ "| Welcome to the Bank, Please follow the below command guideline.                                                                  | \n"
				+ "| -------------------------------------------------------------------------------------------------------------------------------- | \n"
				+ "| Command                           | Description                                                                                  | \n"
				+ "| --------------------------------- | -------------------------------------------------------------------------------------------- | \n"
				+ "| login `<client>`                  | Login as `client`. Creates a new client if not yet exists.                                   | \n"
				+ "| topup `<amount>`                  | Increase logged-in client balance by `amount`.                                               | \n"
				+ "| pay `<another_client>` `<amount>` | Pay `amount` from logged-in client to `another_client`,  maybe in parts, as soon as possible.| \n"
				+ "| exit                              | To exit the application                                                                      | \n"
				+ "| -------------------------------------------------------------------------------------------------------------------------------- | \n");
		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		Bank bank = new Bank();

		System.out.print("Command:");
		while (scanner.hasNext()) {

			String input = scanner.nextLine();
			if (input.equals("exit")) {
				System.out.print("Exiting the application!");
				System.exit(0);
				scanner.close();
			}
			bank.processInput(input);
			System.out.print("Command:");
		}
	}

	public void processInput(String input) {

		if (validInput(input)) {
			String[] command = input.split(" ");
			// if (command.length > 0) {
			String action = command[0].toLowerCase();
			switch (action) {

			case "login":
				loggedIn = command[1];
				accounts = bankService.login(accounts, command[1]);
				break;
			case "pay":
				accounts = bankService.pay(accounts, loggedIn, command[1], Integer.parseInt(command[2]));
				break;
			case "topup":
				accounts = bankService.topup(accounts, loggedIn, Integer.parseInt(command[1]));
				break;
			// Default case statement
			default:
				System.out.println("Invalid input!! Please check the guideline");
			}
			// }
		} else {
			System.out.println("Invalid input!! Please check the guideline");
		}

	}

	public boolean validInput(String input) {
		if (input == null || input.equals(""))
			return false;

		String[] command = input.split(" ");

		if (command.length == 0)
			return false;

		String action = command[0].toLowerCase();
		switch (action) {

		case "login":
			if (command.length != 2)
				return false;
			break;
		case "pay":
			if (command.length != 3)
				return false;
			if (!isInt(command[2]))
				return false;
			break;
		case "topup":
			if (command.length != 2)
				return false;
			if (!isInt(command[1]))
				return false;
			break;
		}

		return true;
	}

	private boolean isInt(String value) {
		try {
			Integer.parseInt(value);

		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
