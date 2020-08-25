package com.revature.services;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.repositories.AccountDAO;
import com.revature.repositories.IAccountDAO;

public class AccountService {

	private IAccountDAO accountDAO;

	public AccountService() {
		super();
		this.accountDAO = new AccountDAO();
	}
	
	public AccountService(IAccountDAO accountDAO) {
		super();
		this.accountDAO = accountDAO;
	}
	
	public Account openNewAccount(User user) {
		Account a = new Account(0, 0, user);
		
		int new_id = accountDAO.insert(a);
		
		if (new_id == 0) {
			return null;
			// maybe throw custom exception here
		}
		a.setId(new_id);
		return a;
	}
	
	public boolean closeAccount(Account a) {
		if (a.getBalance() != 0) {
			return false;
		} else {
			return accountDAO.delete(a.getId());
		}
	}
	
	public boolean withdraw(Account account, double accAmount) {
		if (accAmount > 0 && account.getBalance() - accAmount >= 0) {
			account.setBalance(account.getBalance() - accAmount);
			
			accountDAO.update(account);
			
			return true;
		} else {
			return false;
		}
	}
	
	public boolean deposit(Account account, double depAmount) {
		if (depAmount > 0) {
			account.setBalance(account.getBalance() + depAmount);
			
			accountDAO.update(account);
			return true;
		} else { 
			return false;
		}
	}
	
	public boolean transfer(Account sourceAccount, Account targetAccount, double transferAmount) {
		if (transferAmount > 0 && sourceAccount.getBalance()-transferAmount >= 0) {
			withdraw(sourceAccount, transferAmount);
			deposit(targetAccount, transferAmount);
			return true;
		} else {
			return false;
		}
	}
	
	public Account findAccount(User u) {
		List <Account> accounts = accountDAO.findAll();
		
		for (Account acc : accounts) {
			if (acc.getOwner().equals(u)) {
				return acc;
			}
		}
		return null;
	}
}
