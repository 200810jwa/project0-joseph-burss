package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

public class AccountDAO implements IAccountDAO {

	private IUserDAO userDao = new UserDAO();

	@Override
	public List<Account> findAll() {
		List<User> allUsers = userDao.findAll(); // Potentially unsorted

		List<Account> allAccounts = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()) {

			Statement stmt = conn.createStatement();

			String sql = "SELECT * FROM project0.accounts";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt("id");
				double balance = rs.getDouble("balance");
				int ownerId = rs.getInt("owner");

				// find the User object in allUsers that matches the ownerId;
				User owner = null;
				// If there is no owner, then the Account object will have a null value for the
				// owner
				// Which makes sense
				// Alternatively, we could perform encapsulation within the Account class if we
				// anticipate that all accounts should have an owner
				for (int i = 0; i < allUsers.size(); i++) {
					if (allUsers.get(i).getId() == ownerId) {
						owner = allUsers.get(i);
					}
				}
				Account a = new Account(id, balance, owner);
				allAccounts.add(a);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("WE FAILED TO RETRIEVE ALL ACCOUNTS");
			return null;
		}

		return allAccounts;
	}

	@Override
	public Account findById(int id) {
		Account acc = null;
		try (Connection conn = ConnectionUtil.getConnection()) {

			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM project0.accounts";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int myId = rs.getInt("id");
				double balance = rs.getDouble("balance");
				int owner = rs.getInt("owner");
				
				//TODO: figure out how to handle this...
				// acc = new Account(id, balance, owner);
			}

			return acc;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO FIND USER");
			return null;
		}

	}

	@Override
	public int insert(Account a) {
		String sql = "INSERT INTO project0.accounts (balance, owner) VALUES (?, ?) RETURNING project0.accounts.id";

		try (Connection conn = ConnectionUtil.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			stmt.setDouble(1, a.getBalance()); // PreparedStatement will prevent any content from
			// being executed as SQL
			stmt.setInt(2, a.getOwner().getId());

			ResultSet rs;
			if ((rs = stmt.executeQuery()) != null) {
				rs.next();
				int id = rs.getInt(1);
				return id;
			}

		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("WE FAILED TO INSERT ACCOUNT");
		}

		return 0;
	}

	@Override
	public boolean update(Account a) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "DELETE FROM project0.accounts WHERE id= '" + id + "'";

			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			if (stmt.execute()) {
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				System.out.println("RECORD DELETE SUCCESSFUL");
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("RECORD DELETE FAILURE");
		}
		return false;
	}

	@Override
	public User findByOwner(int owner) {
		User u = null;
		try (Connection conn = ConnectionUtil.getConnection()) 
		{
		
		Statement stmt = conn.createStatement();
		
		String sql = "SELECT * FROM project0.users WHERE id = '"+ owner +"'";
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next())
		{
			int id = rs.getInt("id");
			String myUsername = rs.getString("username");
			String password = rs.getString("password");
			Role role = Role.valueOf(rs.getString("role"));
				
			u = new User(id, myUsername, password, role);
			
		}
		
		return u;
		
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.out.println("Failed to find user");
			return null;
		}
		
	}
}