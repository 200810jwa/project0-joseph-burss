package com.revature;

import java.util.Scanner;

public class Customer extends User{
	
	private String username;
	private String password;

	void registerCustomer() {
		
		Scanner customerInfoScanner = new Scanner(System.in);
		username = customerInfoScanner.next();
		password = customerInfoScanner.next();
		customerInfoScanner.close();
		
	}
	
	private void createAccout() {
		
		
	}
	
	private void transferBalance() {
		
	}
	
	private void depositFunds() {
		
		
	}
	
	private void withdrawFunds() {
		
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
