package com.arun.rajora.chat.chit.bingo.family.bingofamily.Model;

/**
 * Created by arc on 16/4/17.
 */

public class Account {
	private String name;
	private String account_no;
	private String balance;

	public Account() {
	}

	public Account(String name, String account_no, String balance) {
		this.name = name;
		this.account_no = account_no;
		this.balance = balance;
	}

	public String getName() {
		return name;
	}

	public String getAccount_no() {
		return account_no;
	}

	public String getBalance() {
		return balance;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
}
