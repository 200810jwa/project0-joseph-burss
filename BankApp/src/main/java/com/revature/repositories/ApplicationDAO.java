package com.revature.repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Application;
import com.revature.models.User;
import com.revature.utils.ConnectionUtil;

public class ApplicationDAO implements IApplicationDAO {

	private IUserDAO UserDAO = new UserDAO();
	
	@Override
	public List<Application> findAll() {
		List<User> allUsers = UserDAO.findAll(); // Potentially unsorted

		List<Application> allCurrentApplications = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()) {

			Statement stmt = conn.createStatement();

			String sql = "SELECT * FROM project0.applications";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt("id");
				int ownerID = rs.getInt("owner");
				boolean isActive = rs.getBoolean("active_status");

				// find the User object in allUsers that matches the ownerID
				User owner = null;
				// If there is no owner, then the Application object will have a null value for
				// the owner
				for (int i = 0; i < allUsers.size(); i++) {
					if (allUsers.get(i).getId() == ownerID) {
						owner = allUsers.get(i);
					}
				}

				Application a = new Application(id, owner, isActive);

				allCurrentApplications.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO RETRIEVE ALL APPLICATIONS");
			return null;
		}

		return allCurrentApplications;
	}

	@Override
	public Application findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(Application a) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean update(Application a) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}

}
