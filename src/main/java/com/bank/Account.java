package com.bank;

import java.util.LinkedHashMap;
import java.util.Map;

public class Account {

	String name;
	String number;
	int balance;
	Map<String, Integer> debts;

	public Account(String name, int balance) {
		super();
		this.name = name;
		this.balance = balance;
		this.debts = new LinkedHashMap<String, Integer>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public Map<String, Integer> getDebts() {
		return debts;
	}

	public void setDebts(Map<String, Integer> debts) {
		this.debts = debts;
	}

}
