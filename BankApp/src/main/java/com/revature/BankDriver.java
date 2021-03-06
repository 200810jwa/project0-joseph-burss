package com.revature;

import java.util.List;
import java.util.Scanner;

import com.revature.models.Account;
import com.revature.models.Application;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.AccountDAO;
import com.revature.repositories.UserDAO;
import com.revature.services.AccountService;
import com.revature.services.ApplicationService;
import com.revature.services.UserService;

public class BankDriver {

	private static Scanner userInput = new Scanner(System.in);
	private static int userId = 1;
	
	public static void main(String[] args) {

		initialPrompt();
		userInput.close();
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
			break;
		default:
			System.out.println("That was not a valid option.  Please try again.");
			initialPrompt();
			break;
		}
	}

	private static void userRegister() {

		UserService uService = new UserService();

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

		User newUser = uService.register(generateUserId(), firstName, lastName, userName, userPassword, userEmail,
				Role.Customer);

		System.out.println("New user created successfully!");
		determineUserRole(newUser);
	}

	private static void userLogin() {
		UserService uService = new UserService();

		System.out.println("Please enter your user name:");
		String userName = userInput.nextLine();
		System.out.println("Please enter your password:");
		String userPassword = userInput.nextLine();
		User returningUser = uService.login(userName, userPassword);
		determineUserRole(returningUser);
	}

	private static int generateUserId() {

		// Generating a random userId
		// +2 to ensure that 0 or 1 never get chosen, reduce primary key repeat error
		// chance.
		userId = (int) ((Math.random() * 10000) + 2);
		return userId;
	}

	private static void determineUserRole(User currentUser) {

		if (currentUser.getRole() == Role.Customer) {
			System.out.println("Welcome, valued customer");
			System.out.println("===================================");
			customerPrompt(currentUser);
		} else if (currentUser.getRole() == Role.Employee) {
			System.out.println("Welcome back, valued employee");
			System.out.println("===================================");
			employeePrompt(currentUser);
		} else if (currentUser.getRole() == Role.Admin) {
			System.out.println("Welcome back, administrator.");
			System.out.println("===================================");
			adminPrompt(currentUser);
		} else {
			System.out.println("Wait.  What?  How did you even get here?");
		}
	}

	private static void customerPrompt(User currentUser) {
		System.out.println("What do you want to do today, customer?");
		System.out.println("=======================================");
		System.out.println("1. Apply for an account");
		System.out.println("2. Withdraw from existing account");
		System.out.println("3. Deposit into existing account");
		System.out.println("4. Transfer funds between accounts (must have 2 or more active accounts!)");
		System.out.println("5. Log-out");

		String input = userInput.nextLine();
		ApplicationService appService = new ApplicationService();
		AccountService accService = new AccountService();
		Account currentAccount = accService.findAccount(currentUser);

		switch (input) {
		case "1":
			// apply
			appService.apply(currentUser);
			System.out.println("Account application created!");
			System.out.println("Awaiting approval...");
			System.out.println("... returning to customer menu...");
			System.out.println("...");
			System.out.println("");
			customerPrompt(currentUser);
			break;
		case "2":
			// withdraw
			System.out.println("Please enter the amount you want to withdraw:");
			double withdrawAmount = userInput.nextDouble();
			accService.withdraw(currentAccount, withdrawAmount);
			break;
		case "3":
			// deposit
			System.out.println("Please enter the amount you want to deposit into your account:");
			double depositAmount = userInput.nextDouble();
			if (depositAmount > 0) {
				accService.deposit(currentAccount, depositAmount);
			} else {
				System.out.println("Invalid deposit amount...");
				System.out.println("Try again:");
				depositAmount = userInput.nextDouble();
				accService.deposit(currentAccount, depositAmount);
			}
			break;
		case "4":
			// transfer
			break;
		case "5":
			// logout
			logout(currentUser);
			break;
		default:
			System.out.println("That was not a valid option.  Please try again.");
			System.out.println("...");
			System.out.println("Returning to customer menu...");
			customerPrompt(currentUser);
			break;
		}
	}

	private static void employeePrompt(User currentUser) {
		//
		System.out.println("What do you want to do today, employee?");
		System.out.println("=======================================");
		System.out.println("1. View current customers");
		System.out.println("2. View customer accounts");
		System.out.println("3. Approve account applications");
		System.out.println("4. Log-out");
		
		String input = userInput.nextLine();
		UserDAO userDAO = new UserDAO();
		//ApplicationService appService = new ApplicationService();
		//AccountService accService = new AccountService();
		AccountDAO accountDAO = new AccountDAO();
		
		switch (input) {
		// view all customers
		case "1":
			List<User> allUsers = userDAO.findAll();
			System.out.println(allUsers);
			break;
		// view all customer accounts
		case "2":
			List<Account> allAccounts = accountDAO.findAll();
			System.out.println(allAccounts);
			break;
		// Approve account applications
		case "3":
			applicationProcessor();
			break;
		// log out
		case "4":
			logout(currentUser);
			break;
		default:
			System.out.println("That was not a valid option.  Please try again.");
			System.out.println("...");
			System.out.println("Returning to employee menu...");
			employeePrompt(currentUser);
			break;
		}
	}

	private static void adminPrompt(User currentUser) {
		//
		System.out.println("What do you want to do today, administrator?");
		System.out.println("=======================================");
		System.out.println("1. View current customers");
		System.out.println("2. Approve account applications");
		System.out.println("3. Log-out");
		
		String input = userInput.nextLine();
		
		switch (input) {
		// view all customers
		case "1":
			adminAccountActionPrompt();
			break;
		// approve account applications
		case "2":
			applicationProcessor();
			break;
		// log out
		case "3":
			logout(currentUser);
			break;
		default:
			System.out.println("That was not a valid option.  Please try again.");
			System.out.println("...");
			System.out.println("Returning to admin menu...");
			adminPrompt(currentUser);
			break;
		}
	}

	private static void logout(User currentUser) {
		currentUser = null;
		System.out.println("Goodbye!  Thank you for using [NAME REDACTED] banking services!");
		System.out.println("Returning to main menu...");
		System.out.println("...");
		initialPrompt();
	}
	
	private static void applicationProcessor() {
		ApplicationService appService = new ApplicationService();

		List<Application> activeApplications = appService.viewActiveApplications();
		for (int i = 0; i < activeApplications.size(); i++) {
			
			System.out.println(activeApplications.get(i));
			System.out.println("1. Approve application");
			System.out.println("2. Deny application");
			String input = userInput.next();
			switch (input) {
			case "1":
				appService.approveApplication(activeApplications.get(i));
				System.out.println("Application approved.");
				break;
			case "2":
				appService.denyApplication(activeApplications.get(i));
				System.out.println("Application denied.");
			default:
				break;
			}
			
		}
	}
	
	private static void adminAccountActionPrompt() {
		UserDAO userDAO = new UserDAO();
		AccountService accService = new AccountService();
		List<User> allUsers = userDAO.findAll();
		System.out.println(allUsers);
		System.out.println("Who's account would you like to access?  Enter the User ID");
		int input = userInput.nextInt();
		User otherUser = userDAO.findById(input);
		Account currAccount = accService.findAccount(otherUser);
		System.out.println("This user was chosen:");
		System.out.println(otherUser);
		System.out.println("What do you want to do to this User's account?");
		System.out.println("=============================================");
		System.out.println("1. Withdraw from this account");
		System.out.println("2. Deposit into this account");
		System.out.println("3. Cancel this account");
		String newAdminInput = userInput.next();
		switch (newAdminInput) {
		case "1":
			System.out.println("Please enter the amount you want to withdraw:");
			double withdrawAmount = userInput.nextDouble();
			accService.withdraw(currAccount, withdrawAmount);
			break;
		case "2":
			System.out.println("Please enter the amount you want to deposit into your account:");
			double depositAmount = userInput.nextDouble();
			if (depositAmount > 0) {
				accService.deposit(currAccount, depositAmount);
			} else {
				System.out.println("Invalid deposit amount...");
				System.out.println("Try again:");
				depositAmount = userInput.nextDouble();
				accService.deposit(currAccount, depositAmount);
			}
			break;
		case "3":
			accService.closeAccount(currAccount);
			System.out.println("Account closed.");
			break;
		default:
			System.out.println("That was not a valid input. ");
			System.out.println("Please try again...");
			System.out.println("...");
			adminAccountActionPrompt();
			break;
		}
	}
}