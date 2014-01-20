package com.imanafrica.rest.services;

import java.util.GregorianCalendar;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imanafrica.core.domain.Account;
import com.imanafrica.core.domain.User;
import com.imanafrica.core.services.AccountService;
import com.imanafrica.rest.domain.AccountResource;

@Service
public class AccountRestService {
	@Autowired
	AccountService accountService;

	public AccountResource createAccount(AccountResource accountResource, User user){
		Account account = new Account();
		BeanUtils.copyProperties(accountResource, account);
		account.setUser(user);
		account.setCreationdate(new GregorianCalendar().getTime());
		
		account = accountService.createAccount(account);
		BeanUtils.copyProperties(account, accountResource);
		
		return  accountResource;	
	}

	public AccountResource getAccountForUser(int userid) {
		Account account = accountService.getAccountByUserUserid(userid);
		AccountResource accountResource = new AccountResource();
		BeanUtils.copyProperties(account, accountResource);
		return accountResource;
	}
}
