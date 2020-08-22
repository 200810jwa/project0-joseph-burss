package com.revature.models;

import java.util.Objects;

public class User {

	private int id;
	private String fir_name;
	private String last_name;
	private String username;
	private String password;
	private String email;
	private Role role;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int id, String fir_name, String last_name, String username, String password, String email, Role role) {
		super();
		this.id = id;
		this.fir_name = fir_name;
		this.last_name = last_name;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFir_name() {
		return fir_name;
	}

	public void setFir_name(String fir_name) {
		this.fir_name = fir_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, fir_name, id, last_name, password, role, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(fir_name, other.fir_name) && id == other.id
				&& Objects.equals(last_name, other.last_name) && Objects.equals(password, other.password)
				&& role == other.role && Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fir_name=" + fir_name + ", last_name=" + last_name + ", username=" + username
				+ ", password=" + password + ", email=" + email + ", role=" + role + "]";
	}

}
