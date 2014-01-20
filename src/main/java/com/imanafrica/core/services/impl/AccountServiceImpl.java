package com.imanafrica.core.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.imanafrica.core.dao.AccountDao;
import com.imanafrica.core.domain.Account;
import com.imanafrica.core.domain.User;
import com.imanafrica.core.services.AccountService;

@Repository
public class AccountServiceImpl implements AccountService {
	@Autowired
	AccountDao accountDao;

	@Override
	public List<Account> getAccountByUser(User user) {
		return accountDao.findByUser(user);
	}

	@Override
	public Account createAccount(Account account) {
		return accountDao.saveAndFlush(account);
	}

	@Override
	public Account updateBalance(String username, double amount) {
		Account account = accountDao.findByUserUsername(username);
		account.updateBalance(amount);
		account = accountDao.save(account);
		return account;
	}

	@Override
	public Account getAccountByUserUserid(int userid) {
		return accountDao.findByUserUserid(userid);
	}

	@Override
	public Account getAccountById(int accountid) {
		return accountDao.findOne(accountid);
	}


}
