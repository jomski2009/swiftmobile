package com.imanafrica.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imanafrica.core.domain.Account;
import com.imanafrica.core.domain.User;

public interface AccountDao extends JpaRepository<Account, Integer> {
	public List<Account> findByUser(User user);
	public Account findByUserUsername(String username);
	public Account findByUserUserid(int userid);
}
