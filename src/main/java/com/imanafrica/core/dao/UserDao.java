package com.imanafrica.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imanafrica.core.domain.User;

/**
 * 
 * @author Jome Akpoduado
 * 
 */
public interface UserDao extends JpaRepository<User, Integer> {
	/**
	 * Gets a customer with a supplied username
	 * 
	 * @param String
	 *            username
	 * @return Customer
	 */
	List<User> findByUsernameStartingWith(String username);

	/**
	 * Gets a customer with a supplied email address
	 * 
	 * @param email
	 * @return Customer
	 */
	User findByEmail(String email);

	User findByUsername(String username);

}
