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
		User u = userDao.findByUsername(username);

		if (u.getPassword().equals(password)) {
			return u;
		}

		System.out.println("FAILED TO LOGIN");
		return null;
	}

	public User register(Integer id, String firstName, String lastName, String username, String password, String email,
			Role role) {
		User u = new User(id, firstName, lastName, username, password, email, role);

		int new_id = userDao.insert(u);

		return u;
	}

	public boolean deleteUser(User u) {
		int id = u.getId();
		return userDao.delete(id);
	}

	public boolean changePassword(int id, String newPassword) {
		User u = userDao.findById(id);

		u.setPassword(newPassword);

		return userDao.update(u);
	}

}