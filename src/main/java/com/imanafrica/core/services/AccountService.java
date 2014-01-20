package com.imanafrica.core.services;

import java.util.List;

import com.imanafrica.core.domain.Account;
import com.imanafrica.core.domain.User;

public interface AccountService {
	public List<Account> getAccountByUser(User user);
		
	public Account createAccount(Account account);
	
	public Account updateBalance(String username, double amount);

	public Account getAccountByUserUserid(int userid);

	public Account getAccountById(int accountid);


}
