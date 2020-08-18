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
		
		int ut = userType.nextInt();
		
		if (ut == 1) {
			
			User bankUser = new Customer();
			System.out.println("Thank you!  We are glad you have returned.  "
					+ "Please enter your username and password.");
			userType.close();
			
		} else if (ut == 2) {
			
			User bankUser = new Employee();
			System.out.println("Welcome back, valued employee!");
			userType.close();

			
		} else if (ut == 3) {
			
			User bankUser = new Administrator();
			System.out.println("Welcome back, Mr. Administrator.");
			userType.close();

		
		} else {
			
			System.out.println("Invalid input:  Please try again.");
			getUserType();
		}
	}

	
	
	
}
