package com.bank;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BankServiceImplTest {

	Map<String, Account> accounts;
	BankServiceImpl bankService;
	String loggedIn;

	@Before
	public void init() {
		accounts = new HashMap<String, Account>();
		bankService = new BankServiceImpl();
		loggedIn = "";
	}

	@After
	public void teardown() {
		accounts.clear();
		loggedIn = "";
	}

	@Test
	public void login_shouldCreateUser() {
		accounts = bankService.login(accounts, "Harry");
		
		assertEquals(1, accounts.size());
		assertEquals("Harry", accounts.get("Harry").getName());
		assertEquals(0, accounts.get("Harry").getBalance());
	}
	
	@Test
	public void topup_shouldTopupBalance() {
		accounts = bankService.login(accounts, "Harry");
		loggedIn = "Harry";
		accounts = bankService.topup(accounts, loggedIn, 100);
		
		assertEquals("Harry", accounts.get("Harry").getName());
		assertEquals(100, accounts.get("Harry").getBalance());
	}
	
	@Test
	public void topup_shouldTransferToDebt() {
		accounts = bankService.login(accounts, "Harry");
		loggedIn = "Harry";
		accounts = bankService.topup(accounts, loggedIn, 100);
		
		accounts = bankService.login(accounts, "Tom");
		loggedIn = "Tom";
		accounts = bankService.topup(accounts, loggedIn, 80);
		accounts = bankService.pay(accounts, loggedIn, "Harry", 50);
		accounts = bankService.pay(accounts, loggedIn, "Harry", 100);
		accounts = bankService.topup(accounts, loggedIn, 30);
		
		assertEquals("Tom", accounts.get("Tom").getName());
		assertEquals(0, accounts.get("Tom").getBalance());
		assertEquals(Integer.valueOf(40), accounts.get("Tom").getDebts().get("Harry"));
		
		assertEquals("Harry", accounts.get("Harry").getName());
		assertEquals(210, accounts.get("Harry").getBalance());	
	}
	
	@Test
	public void topup_shouldClearDues() {
		accounts = bankService.login(accounts, "Harry");
		loggedIn = "Harry";
		accounts = bankService.topup(accounts, loggedIn, 100);
		
		accounts = bankService.login(accounts, "Tom");
		loggedIn = "Tom";
		accounts = bankService.topup(accounts, loggedIn, 80);
		accounts = bankService.pay(accounts, loggedIn, "Harry", 50);
		accounts = bankService.pay(accounts, loggedIn, "Harry", 100);
		accounts = bankService.topup(accounts, loggedIn, 30);
		
		accounts = bankService.login(accounts, "Harry");
		loggedIn = "Harry";
		accounts = bankService.pay(accounts, loggedIn, "Tom", 30);
		
		accounts = bankService.login(accounts, "Tom");
		loggedIn = "Tom";
		accounts = bankService.topup(accounts, loggedIn, 100);
		
		assertEquals("Tom", accounts.get("Tom").getName());
		assertEquals(90, accounts.get("Tom").getBalance());
		
		assertEquals("Harry", accounts.get("Harry").getName());
		assertEquals(220, accounts.get("Harry").getBalance());
	}
	
	@Test
	public void pay_shouldTransferFull() {
		accounts = bankService.login(accounts, "Harry");
		loggedIn = "Harry";
		accounts = bankService.topup(accounts, loggedIn, 100);
		
		accounts = bankService.login(accounts, "Tom");
		loggedIn = "Tom";
		accounts = bankService.topup(accounts, loggedIn, 80);
		accounts = bankService.pay(accounts, loggedIn, "Harry", 50);
		
		assertEquals("Tom", accounts.get("Tom").getName());
		assertEquals(30, accounts.get("Tom").getBalance());
		
		assertEquals("Harry", accounts.get("Harry").getName());
		assertEquals(150, accounts.get("Harry").getBalance());		
	}
	
	@Test
	public void pay_shouldTransferByParts() {
		accounts = bankService.login(accounts, "Harry");
		loggedIn = "Harry";
		accounts = bankService.topup(accounts, loggedIn, 100);
		
		accounts = bankService.login(accounts, "Tom");
		loggedIn = "Tom";
		accounts = bankService.topup(accounts, loggedIn, 80);
		accounts = bankService.pay(accounts, loggedIn, "Harry", 50);
		accounts = bankService.pay(accounts, loggedIn, "Harry", 100);
		
		assertEquals("Tom", accounts.get("Tom").getName());
		assertEquals(0, accounts.get("Tom").getBalance());
		assertEquals(Integer.valueOf(70), accounts.get("Tom").getDebts().get("Harry"));
		
		assertEquals("Harry", accounts.get("Harry").getName());
		assertEquals(180, accounts.get("Harry").getBalance());
	}
	
	@Test
	public void pay_shouldTransferDues() {
		accounts = bankService.login(accounts, "Harry");
		loggedIn = "Harry";
		accounts = bankService.topup(accounts, loggedIn, 100);
		
		accounts = bankService.login(accounts, "Tom");
		loggedIn = "Tom";
		accounts = bankService.topup(accounts, loggedIn, 80);
		accounts = bankService.pay(accounts, loggedIn, "Harry", 50);
		accounts = bankService.pay(accounts, loggedIn, "Harry", 100);
		accounts = bankService.topup(accounts, loggedIn, 30);
		
		accounts = bankService.login(accounts, "Harry");
		loggedIn = "Harry";
		accounts = bankService.pay(accounts, loggedIn, "Tom", 30);
		
		assertEquals("Tom", accounts.get("Tom").getName());
		assertEquals(0, accounts.get("Tom").getBalance());
		assertEquals(Integer.valueOf(10), accounts.get("Tom").getDebts().get("Harry"));
		
		assertEquals("Harry", accounts.get("Harry").getName());
		assertEquals(210, accounts.get("Harry").getBalance());		
	}
}
