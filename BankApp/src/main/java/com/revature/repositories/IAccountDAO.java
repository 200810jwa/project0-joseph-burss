package com.revature.repositories;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.User;

public interface IAccountDAO {

	public List<Account> findAll();

	public Account findById(int id);

	public int insert(Account a);

	public boolean update(Account a);

	public boolean delete(int id);
}