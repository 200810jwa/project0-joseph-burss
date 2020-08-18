package com.revature;

import java.util.Scanner;
import com.revature.Customer;
import com.revature.User;
import com.revature.Employee;
import com.revature.Administrator;

public class BankDriver {

	
	
	public static void main(String[] args) {
		
		// Prompting the user to enter what kind of bank user they are.
		System.out.println("Welcome to [NAME REDACTED] Banking Service!");
		System.out.println("What kind of user are you?  Customer(1), Employee(2), Administrator(3)");
		
		// Getting the type of user.
		getUserType(); 
		
		

	}

	//TODO:  
	//edit this to allow the main to see what kind of user was created.
	private static User getUserType() {
		
		Scanner userType = new Scanner(System.in);
		
		int ut = userType.nextInt();
		
		if (ut == 1) {
			
			Customer bankUser = new Customer();
			System.out.println("Thank you!  We are glad you have returned. ");
			userType.close();
			return bankUser;
			
		} else if (ut == 2) {
			
			Employee bankUser = new Employee();
			System.out.println("Welcome back, valued employee!");
			userType.close();
			return bankUser;
			
		} else if (ut == 3) {
			
			Administrator bankUser = new Administrator();
			System.out.println("Welcome back, Mr. Administrator.");
			userType.close();
			return bankUser;
		
		} else {
			
			System.out.println("Invalid input:  Please try again.");
			getUserType();
		}
		return null;
	}

	
	
	
}
