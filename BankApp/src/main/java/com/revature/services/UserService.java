package com.revature.services;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.IUserDAO;
import com.revature.repositories.UserDAO;

public class UserService {
	
	private IUserDAO userDao;
	
	// This constructor will be used throughout our normal application
	// which does use the real DAO
	public UserService() {
		super();
		this.userDao = new UserDAO();
	}
	
	// In order to leverage a mocking library such as Mockito, we will need to
	// inject a fake instance of our dependencies
	// This will be done through one of our constructors
	// Where we will hand the constructor the fake DAO
	
	// This way in our tests, the DAO methods will not actually
	// use our database
	public UserService(IUserDAO userDao) {
		super();
		this.userDao = userDao;
	}

	public User login(String username, String password) {
		return null;
	}
	
	public User register(String username, String password, Role role) {
		User u = new User(0, username, password, role);
		
		int new_id = userDao.insert(u);
		
		if(new_id == 0) {
			// Insert failed
			return null;
			// Maybe throw a custom exception
		}
		
		u.setId(new_id);
		
		return u;
	}

	public boolean changePassword(int id, String newPassword) {
		// TODO Auto-generated method stub
		return false;
	}
}