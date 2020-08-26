package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

public class UserDAO implements IUserDAO {
	// We will follow a Data Access Object Design Pattern
	// This class will have instance methods
	// whose responsibility is to perform common CRUD operations
	// against the database as needed

	// Common operations:
	// findAll
	// findByUsername
	// update
	// insert
	// delete

	// We will declare a method for each of the above operations
	// Because of this consistency in needed CRUD operations
	// It is not uncommon to create an interface for our DAO classes

	@Override
	public List<User> findAll() {
		List<User> allUsers = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()) {

			Statement stmt = conn.createStatement();

			String sql = "SELECT * FROM project0.USERS";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt("id");
				String first_name = rs.getString("first_name");
				String last_nameString = rs.getString("last_name");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String email = rs.getString("email");
				Role role = Role.valueOf(rs.getString("role"));

				User u = new User(id, first_name, last_nameString, username, password, email, role);
				allUsers.add(u);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("WE FAILED TO RETRIEVE ALL USERS");
			return null;
		}

		return allUsers;
	}

	@Override
	public User findByUsername(String username) {
		User u = null;

		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM project0.users WHERE project0.users.username = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, username);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				u = new User();
				u.setId(rs.getInt("id"));
				u.setFir_name(rs.getString("first_name"));
				u.setLast_name(rs.getString("last_name"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("password"));
				u.setEmail(rs.getString("email"));
				u.setRole(Role.valueOf(rs.getString("role")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO RETRIEVE USER");
			return null;
		}

		return u;
	}

	@Override
	public int insert(User u) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "INSERT INTO project0.users (id, username, password, first_name, last_name, email, role) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING project0.users.id";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, u.getId());
			stmt.setString(2, u.getUsername());
			stmt.setString(3, u.getPassword());
			stmt.setString(4, u.getFir_name());
			stmt.setString(5, u.getLast_name());
			stmt.setString(6, u.getEmail());
			stmt.setObject(7, u.getRole(), Types.OTHER);

			ResultSet rs;
			if ((rs = stmt.executeQuery()) != null) {
				rs.next();

				int id = rs.getInt(1);

				return id;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("WE FAILED TO INSERT USER");
		}

		return 0;
	}

	@Override
	public boolean update(User u) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "UPDATE project0.users SET id = ?, username = ?, password = ?, firstname = ?, lastname = ?, email = ?, role = ? WHERE project0.users.id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, u.getId());
			stmt.setString(2, u.getUsername());
			stmt.setString(3, u.getPassword());
			stmt.setString(4, u.getFir_name());
			stmt.setString(5, u.getLast_name());
			stmt.setString(6, u.getEmail());
			stmt.setObject(7, u.getRole(), Types.OTHER);

			if (stmt.executeUpdate(sql) != 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO UPDATE THE USER");
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "DELETE project0.users WHERE project0.users.id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, id);

			if (stmt.executeUpdate(sql) != 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO DELETE USER");
		}
		return false;
	}

	@Override
	public User findById(int id) {
		User u = null;

		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM project0.users WHERE project0.users.id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				u = new User();

				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("password"));
				u.setFir_name(rs.getString("first_name"));
				u.setLast_name(rs.getString("last_name"));
				u.setEmail(rs.getString("email"));
				u.setRole(Role.valueOf(rs.getString("role")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO RETRIEVE USER");
			return null;
		}

		return u;
	}
}