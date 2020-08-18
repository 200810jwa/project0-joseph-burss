package com.revature;

import java.util.Scanner;
import com.revature.Customer;
import com.revature.User;
import com.revature.Employee;
import com.revature.Administrator;

public class BankDriver {

	public static void main(String[] args) {
		
		System.out.println("Welcome to [NAME REDACTED] Banking Service!");
		System.out.println("What kind of user are you?  Customer(1), Employee(2), Administrator(3)");
		
		getUserType(); 
		

	}

	private static void getUserType() {
		
		Scanner userType = new Scanner(System.in);
		
		if (userType.nextInt() == 1) {
			
			
			
		} else if (userType.nextInt() == 2) {
			
			
			
		} else if (userType.nextInt() == 3) {
			
			
		
		} else {
			
			System.out.println("Invalid input:  Please try again.");
			getUserType();
		}
		
	}

}
