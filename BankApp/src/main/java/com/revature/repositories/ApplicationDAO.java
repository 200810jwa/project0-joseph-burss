package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
	public int insert(Application a) {
		String sql = "INSERT INTO project0.applications (owner, active_status) VALUES (?, ?) RETURNING project0.applications.id";

		try (Connection conn = ConnectionUtil.getConnection() ){;

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, a.getOwner().getId());
			stmt.setBoolean(2, a.isActiveApp());
			ResultSet rs;
			if ((rs = stmt.executeQuery()) != null) {
				rs.next();

				int ownerId = rs.getInt(1);

				return ownerId;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO INSERT APPLICATION");
		}

		return 0; // Invalid primary key
	}

	@Override
	public boolean update(Application a) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "UPDATE project0.applications SET owner = ?, active_status = ? WHERE project0.applications.id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, a.getOwner().getId());
			stmt.setBoolean(2, a.isActiveApp());
			stmt.setInt(3, a.getId());

			if (stmt.executeUpdate() != 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO UPDATE APPLICATION");
		}
		return false;
	}

	@Override
	public Application findById(int id) {
		Application a = null;

		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "SELECT * FROM project0.applications WHERE project0.applications.id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, id);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				a = new Application();

				a.setId(rs.getInt("id"));
				a.setOwner(UserDAO.findById(rs.getInt("owner")));
				a.setActiveApp(rs.getBoolean("active_status"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO RETRIEVE APPLICATION");
			return null;
		}

		return a;
	}

	@Override
	public boolean delete(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "DELETE project0.applications WHERE project0.applications.id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, id);

			if (stmt.executeUpdate(sql) != 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO DELETE APPLICATION");
		}
		return false;
	}

}
