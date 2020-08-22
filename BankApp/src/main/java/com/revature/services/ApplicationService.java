package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import com.revature.repositories.ApplicationDAO;
import com.revature.repositories.IApplicationDAO;
import com.revature.models.Account;
import com.revature.models.Application;
import com.revature.models.User;

public class ApplicationService {

	private IApplicationDAO applicationDAO;
	
	public ApplicationService() {
		super();
		this.applicationDAO = (IApplicationDAO) new ApplicationDAO();
	}
	
	public ApplicationService(IApplicationDAO applicationDAO) {
		super();
		this.applicationDAO = applicationDAO;
	}
	
	public Application findApplication(User u) {
		List <Application> applications = applicationDAO.findAll();
		
		for (Application a : applications) {
			if (a.getOwner().equals(u)) {
				return a;
			}
		}
		return null;
	}
	
	public List<Application> viewActiveApplications() {
		List<Application> activeApps = new ArrayList<>();
		
		List <Application> applications = applicationDAO.findAll();
		for (Application a : applications) {
			if (a.isActiveApp()) {
				activeApps.add(a);
			}
		}
		return activeApps;
	}
	
	public boolean approveApplication(Application app) {
		AccountService accountService = new AccountService();
		Account acc = accountService.openNewAccount(app.getOwner());
		return applicationDAO.delete(app.getId());
	}
	
	public boolean denyApplication(Application app) {
		app.setActiveApp(false);
		return applicationDAO.update(app);
	}
	
	public Application apply(User u) {
		Application app = new Application(0, u, true);
		
		int new_id = applicationDAO.insert(app);
		
		if (new_id == 0) {
			return null;
		}
		app.setId(new_id);
		
		return app;
	}
}