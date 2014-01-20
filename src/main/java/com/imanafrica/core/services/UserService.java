package com.imanafrica.core.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.imanafrica.core.domain.Group;
import com.imanafrica.core.domain.User;
import com.imanafrica.core.domain.UserDetails;
import com.imanafrica.rest.dto.UserAccountCreationDTO;

public interface UserService {

	public void deleteUser(int customerId);

	public User updateUser(User customer);

	public User findUserById(int customerId);

	public User findUserByEmail(String email);

	public List<User> findUserByUsername(String username);

	public List<User> findAll();
	
	public int getCredits();
	
	public Map<String, Object> createUser(UserAccountCreationDTO userDto);
	
	Set<Group> getGroups(User user);

	public User createUser(UserDetails userDetails);

	public User createUser(User user);

}
