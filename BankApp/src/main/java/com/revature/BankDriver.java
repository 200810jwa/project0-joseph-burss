package com.revature;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.Role;
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
		
		User u = userService.register(4, "waffleman", "passwoRd", "wafflesAreGood", "WithSyrup", "waffleLover@sample.com", Role.Employee);
		User t = userService.register(3, "daBomb", "passW0rd", "Louis", "Cunningham", "loucun@sample.com", Role.Admin);
		User w = userService.register(1, "barnacleBoy", "Passw0Rd", "Mike", "Watson", "mwatson@sample.com", Role.Customer);
		User x = userService.register(2, "banklover", "pAssW0rd", "John", "Smith", "jsmith@sample.com", Role.Customer);

		allUsers = userDao.findAll();
		System.out.println(allUsers);
		
		IAccountDAO accountDao = new AccountDAO();
		
		
	}
}