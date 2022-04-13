package com.bank;

import java.util.Map;

public interface BankService {

	public Map<String, Account> login(Map<String, Account> accounts, String username);

	public Map<String, Account> pay(Map<String, Account> accounts, String from, String to, int amount);

	public Map<String, Account> topup(Map<String, Account> accounts, String to, int amt);

}