package com.revature;

import java.util.List;
import java.util.Scanner;

import com.revature.models.Account;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.AccountDAO;
import com.revature.repositories.IAccountDAO;
import com.revature.repositories.IUserDAO;
import com.revature.repositories.UserDAO;
import com.revature.services.UserService;

public class BankDriver {

	private static Scanner userInput = new Scanner(System.in);
	private static int userId = 1;
	
	public static void main(String[] args) {

		initialPrompt();

		
	}

	private static void initialPrompt() {
		System.out.println("WELCOME TO [NAME REDACTED] BANKING SERVICE");
		System.out.println("------------------------------------------");
		System.out.println("Please select an option:");
		System.out.println("1. Register new User.");
		System.out.println("2. Login existing User.");
		System.out.println("3. Exit banking application.");
		
		String input = userInput.nextLine();

		switch (input) {
		case "1":
			userRegister();
			break;
		case "2":
			userLogin();
			break;
		case "3":
			System.out.println("Thank you for using [NAME REDACTED] banking service!");
		default:
			System.out.println("That was not a valid option.  Please try again.");
			initialPrompt();
			break;
		}
	}

	private static void userRegister() {

		UserService uService = new UserService();
		IUserDAO uDAO = new UserDAO();
		
		System.out.println("WELCOME NEW USER!");
		System.out.println("Please register to continue:");
		System.out.println("--------------------------------------");
		System.out.println("Enter your first name:");
		String firstName = userInput.nextLine();
		System.out.println("Enter your last name:");
		String lastName = userInput.nextLine();
		System.out.println("Enter your banking username:");
		String userName = userInput.nextLine();
		System.out.println("Enter a secure password:");
		String userPassword = userInput.nextLine();
		System.out.println("Enter your email address:");
		String userEmail = userInput.nextLine();
		System.out.println("--------------------------------------");
		System.out.println("Thank you for entering your information!");
		System.out.println("Creating new User...");
		
		User newUser = uService.register(userId, firstName, lastName, userName, userPassword, userEmail, Role.Customer);
		
		System.out.println("New user created successfully!");
		determineUserRole(newUser);
		userId++;
	}

	private static void userLogin() {

	}
	
	private static void determineUserRole(User currentUser) {
		
		if (currentUser.getRole() == Role.Customer) {
			System.out.println("Do customer stuff here...");
		} else if (currentUser.getRole() == Role.Employee) {
			System.out.println("Do employee stuff here...");
		} else if (currentUser.getRole() == Role.Admin) {
			System.out.println("Do admin stuff here...");
		} else {
			System.out.println("Wait.  What?  How did you even get here?");
		}
	}
}