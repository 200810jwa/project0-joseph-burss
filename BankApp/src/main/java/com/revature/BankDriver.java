package com.revature;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.repositories.AccountDAO;
import com.revature.repositories.IAccountDAO;
import com.revature.repositories.IUserDAO;
import com.revature.repositories.UserDAO;
import com.revature.services.UserService;

public class BankDriver {

	public static void main(String[] args) {
		UserService userService = new UserService();
		IUserDAO userDao = new UserDAO();

		List<User> allUsers = userDao.findAll();
		
		System.out.println(allUsers);
		
//		User u = userService.register("becky", "pass", Role.Customer);
//		System.out.println(u);
		
		allUsers = userDao.findAll();
		System.out.println(allUsers);
		
		IAccountDAO accountDao = new AccountDAO();
		
		int newId = accountDao.insert(new Account(0, 0, allUsers.get(1))); // A new Account for Becky
		System.out.println(newId);
		
		List<Account> allAccounts = accountDao.findAll();
		
		System.out.println(allAccounts);
	}
}