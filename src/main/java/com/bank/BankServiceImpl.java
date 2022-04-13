package com.bank;

import java.util.HashMap;
import java.util.Map;

public class BankServiceImpl implements BankService {

	@Override
	public Map<String, Account> login(Map<String, Account> accounts, String username) {

		if(accounts == null) accounts = new HashMap<String, Account>(); 
		
		if (!accounts.containsKey(username)) {
			Account acct = new Account(username, 0);
			accounts.put(username, acct);
		}
		System.out.println("Hello, " + username + "!");
		findOwedBy(accounts, username);
		System.out.println("Your balance is " + accounts.get(username).getBalance());
		findOwedTo(accounts.get(username));

		return accounts;
	}

	@Override
	public Map<String, Account> pay(Map<String, Account> accounts, String from, String to, int amt) {

		if (accounts == null || accounts.size() == 0) {
			System.out.println("Please login!!");
			return null;
		}

		if (!validateUser(to, accounts)) {
			System.out.println("Please enter valid user");
			return null;
		}
		int balance = accounts.get(from).getBalance();
		int trfrAmt = 0, dbtAmt = 0;

		// check if rcvr owes to the sender
		if (accounts.get(to).getDebts().containsKey(from)) {
			// deduct from rcvr s debts
			int owingAmt = accounts.get(to).getDebts().get(from);

			if (owingAmt <= amt) {
				accounts.get(to).getDebts().remove(from);
				trfrAmt = amt - owingAmt;
			} else if (owingAmt > amt) {
				// deduct from debt only
				owingAmt -= amt;
				accounts.get(to).getDebts().put(from, owingAmt);
			}

			System.out.println("Owing " + owingAmt + " from " + to);
		} else {
			trfrAmt = amt;
		}

		if (trfrAmt > 0) {

			if (balance >= trfrAmt) {
				dbtAmt = 0;
				balance -= trfrAmt;
			} else {
				dbtAmt = trfrAmt - balance;
				trfrAmt = balance;
				balance = 0;

				if (accounts.get(from).getDebts().containsKey(to)) {
					dbtAmt += accounts.get(from).getDebts().get(to);
				}

				accounts.get(from).getDebts().put(to, dbtAmt);
				System.out.println("Owing " + dbtAmt + " to " + to);
			}

			accounts.get(from).setBalance(balance);
			accounts.get(to).setBalance(accounts.get(to).getBalance() + trfrAmt);
			System.out.println("Transferred " + trfrAmt + " to " + to);
		}
		System.out.println("Your balance is " + accounts.get(from).getBalance());
		return accounts;
	}

	@Override
	public Map<String, Account> topup(Map<String, Account> accounts, String to, int amt) {

		
		if (accounts == null || accounts.size() == 0) {
			System.out.println("Please login before topup");
			return null;
		}

		// check if the user has any debts
		Map<String, Integer> debts = accounts.get(to).getDebts();

		if (debts.size() > 0) {
			// debts exist

			for (Map.Entry<String, Integer> debt : debts.entrySet()) {
				String payee = debt.getKey();
				int value = debt.getValue();
				int newBalance = 0;

				if (amt >= value) {
					amt -= value;
					debts.remove(payee);
					newBalance = accounts.get(payee).getBalance() + value;

					System.out.println("Transferred " + value + " to " + payee);
				} else {
					value -= amt;
					// update the debts
					debts.put(payee, value);
					newBalance = accounts.get(payee).getBalance() + amt;

					System.out.println("Transferred " + amt + " to " + payee);
					System.out.println("Owing " + value + " to " + payee);
					amt = 0;
				}
				// add the balance to the payee
				accounts.get(payee).setBalance(newBalance);

				if (amt == 0)
					break;
			}
			// setting the updated debts
			accounts.get(to).setDebts(debts);
		}

		if (amt > 0) {
			// update the balance, since no debts found
			int updatedBalance = accounts.get(to).getBalance() + amt;
			accounts.get(to).setBalance(updatedBalance);
		}
		System.out.println("Your balance is " + accounts.get(to).getBalance());
		return accounts;
	}

	private void findOwedBy(Map<String, Account> accounts, String username) {
		for (Map.Entry<String, Account> account : accounts.entrySet()) {
			if (!account.getKey().equals(username)) {
				for (Map.Entry<String, Integer> debt : account.getValue().getDebts().entrySet()) {
					if (debt.getKey().equals(username)) {
						System.out.println("Owing " + debt.getValue() + " from " + account.getKey());
					}
				}
			}
		}
	}

	private void findOwedTo(Account account) {
		for (Map.Entry<String, Integer> debt : account.getDebts().entrySet()) {
			System.out.println("Owing " + debt.getValue() + " to " + debt.getKey());

		}
	}

	private boolean validateUser(String user, Map<String, Account> accounts) {

		if (!accounts.containsKey(user))
			return false;

		return true;
	}

}
