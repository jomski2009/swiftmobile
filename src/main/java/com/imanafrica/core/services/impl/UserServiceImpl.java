package com.imanafrica.core.services.impl;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import com.imanafrica.core.dao.AccountDao;
import com.imanafrica.core.dao.GroupDao;
import com.imanafrica.core.dao.UserDao;
import com.imanafrica.core.dao.UserProxyDao;
import com.imanafrica.core.dao.UserRoleDao;
import com.imanafrica.core.domain.Account;
import com.imanafrica.core.domain.Group;
import com.imanafrica.core.domain.User;
import com.imanafrica.core.domain.UserDetails;
import com.imanafrica.core.domain.UserRole;
import com.imanafrica.core.services.UserService;
import com.imanafrica.rest.dto.UserAccountCreationDTO;

@Repository
public class UserServiceImpl implements UserService {
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	UserDao userDao;

	@Autowired
	Environment env;

	@Autowired
	UserRoleDao userRoleDao;

	@Autowired
	UserProxyDao userProxyDao;

	@Autowired
	GroupDao groupDao;

	@Autowired
	AccountDao accountDao;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Override
	public User createUser(UserDetails userDetails) {
		User user = new User();
		BeanUtils.copyProperties(userDetails, user);
		user.setEnabled(true);
		user.setCreateddate(new GregorianCalendar().getTime());

		// Encode the password here...
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);

		return userDao.save(user);
	}

	@Transactional
	public Map<String, Object> createUser(UserAccountCreationDTO userDto) {
		Map<String, Object> result = new HashMap<String, Object>();

		try {
			User user = new User();
			user.setFirstname(userDto.getUser().getFirstname());
			user.setLastname(userDto.getUser().getLastname());
			user.setUsername(userDto.getUser().getUsername());
			user.setCellnumber(userDto.getUser().getCellnumber());
			user.setEmail(userDto.getUser().getEmail());
			user.setEnabled(true);
			user.setCreateddate(new GregorianCalendar().getTime());
			// Encode the password here...
			String password = passwordEncoder.encode(userDto.getUser()
					.getPassword());
			user.setPassword(password);
			// Save the user...
			user = userDao.save(user);
			log.info(">>>>>>>>>>>>User was saved successfully... User id: "
					+ user.getUserid());

			// String query = "Insert into user_roles values (" + user.getId() +
			// ", 'ROLE_USER')";
			// jdbcTemplate.execute(query);
			// userDao.saveUserRole(user.getId(), "ROLE_USER");
			UserRole userRole = new UserRole();
			userRole.setUserid(user.getUserid());
			;
			userRole.setRolename("ROLE_USER");
			log.info(">>>>>>>>>>>>UserRole..." + userRole.toString());

			userRoleDao.save(userRole);
			log.info(">>>>>>>>>>>>UserRole was saved successfully...");

			Account account = new Account();
			account.setName(userDto.getAccount().getName());
			// Add the user to the account object and save the account...
			account.setUser(user);

			// Commenting this code should make the trasaction fail..
			account.setCreationdate(user.getCreateddate());
			accountDao.save(account);
			log.info(">>>>>>>>>>>>Account was saved successfully...");
			// Now get the saved accounts and return the data...
			user.getAccounts().add(account);

			result.put("message", "User successfully created");
			;
			result.put("user", user);
			result.put("status", HttpStatus.CREATED);
			return result;
		} catch (DataIntegrityViolationException dve) {
			log.info("The dve error is >>>>>> : "
					+ dve.getRootCause().getLocalizedMessage());
			result.put("error", dve.getRootCause().getLocalizedMessage());
		} catch (DataAccessException dae) {
			log.info("The dae error is >>>>>> : "
					+ dae.getRootCause().getLocalizedMessage());
			result.put("error", dae.getRootCause().getLocalizedMessage());
		} catch (TransactionSystemException tse) {
			log.info("The tse error is >>>>>> : "
					+ tse.getRootCause().getLocalizedMessage());
			result.put("error", tse.getRootCause().getLocalizedMessage());
		}

		return result;

	}

	@Override
	public void deleteUser(int customerId) {
		userDao.delete(customerId);

	}

	@Override
	public User updateUser(User customer) {
		return userDao.save(customer);
	}

	@Override
	public User findUserById(int customerId) {
		User customer = userDao.findOne(customerId);
		return customer;
	}

	@Override
	public User findUserByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public List<User> findUserByUsername(String username) {

		return userDao.findByUsernameStartingWith(username);
	}

	@Override
	public List<User> findAll() {
		List<User> results = new ArrayList<User>();

		Iterable<User> allUsers = userDao.findAll();

		Iterator<User> it = allUsers.iterator();

		while (it.hasNext()) {
			User c = it.next();
			// List<Account> accounts = accountDao.findByUserId(c.getId());
			// c.setAccounts(null);
			c.setPassword("HIDDEN");
			results.add(c);
		}

		return results;
	}

	@Override
	public int getCredits() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<Group> getGroups(User user) {
		return user.getGroups();
	}

	@Override
	public User createUser(User user) {
		user.setEnabled(true);
		user.setCreateddate(new GregorianCalendar().getTime());

		// Encode the password here...
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		user = userDao.save(user);
		
		//Grant Authorities...
		UserRole userRole = new UserRole();
		userRole.setUserid(user.getUserid());
		userRole.setRolename("ROLE_USER");
		log.info(">>>>>>>>>>>>UserRole..." + userRole.toString());

		userRoleDao.save(userRole);
		log.info(">>>>>>>>>>>>UserRole was saved successfully...");
		
		Account account = new Account();
		account.setName(user.getFirstname() + " " + user.getLastname());
		// Add the user to the account object and save the account...
		account.setUser(user);
		account.setCreationdate(user.getCreateddate());
		account = accountDao.save(account);
		log.info(">>>>>>>>>>>>Account was saved successfully...");
		// Now get the saved accounts and return the data...
		user.getAccounts().add(account);

		return user;
	}
}
