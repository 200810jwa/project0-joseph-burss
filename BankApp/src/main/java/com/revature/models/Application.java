package com.revature.models;

import java.util.Objects;

public class Application {

	private int id;
	private User owner;
	private boolean isActiveApp;
	
	public Application() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Application(int id, User owner, boolean isActiveApp) {
		super();
		this.id = id;
		this.owner = owner;
		this.isActiveApp = isActiveApp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, owner);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Application)) {
			return false;
		}
		Application other = (Application) obj;
		return id == other.id && Objects.equals(owner, other.owner);
	}

	@Override
	public String toString() {
		return "Application [id=" + id + ", owner=" + owner + "]";
	}

	public boolean isActiveApp() {
		return isActiveApp;
	}
	
	public void setActiveApp(boolean isActive) {
		this.isActiveApp = isActive;
	}
	
}
