package com.imanafrica.rest.v1.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.imanafrica.core.domain.Account;
import com.imanafrica.core.domain.User;
import com.imanafrica.core.services.AccountService;
import com.imanafrica.core.services.SmsService;
import com.imanafrica.core.services.UserService;
import com.imanafrica.rest.domain.UserResource;
import com.imanafrica.rest.domain.assemblers.UserResourceAssembler;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
	Logger log = Logger.getLogger(this.getClass());

	@Autowired
	UserService userService;

	@Autowired
	SmsService smsService;

	@Autowired
	AccountService accountService;
	
	@Autowired
	UserResourceAssembler userResourceAssembler;

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "users/list", method = RequestMethod.GET)
	public ResponseEntity<List<UserResource>> getAllUsers() {

		List<UserResource> users =userResourceAssembler.toResources(userService.findAll());

		// TODO: Send the Rest user object instead...

		return new ResponseEntity<List<UserResource>>(users, HttpStatus.OK);
	}

	/**
	 * 
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "users/{username}", method = RequestMethod.GET)
	public List<User> getUserByUsername(@PathVariable String username) {
		List<User> users = userService.findUserByUsername(username);

		return users;
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "getbalance", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getBalance() {
		// TODO: Get current credit balance...
		Map<String, Object> result = new HashMap<String, Object>();

		double balance = smsService.getCredits();
		result.put("current balance", balance);
		result.put("sms credits", (int) Math.floor(balance / 0.18));

		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}

	/**
	 * 
	 * @param String
	 *            username
	 * @param double amount
	 * @return The updated balance for the account holder
	 */
	@RequestMapping(value = "addcredit/{username}/{amount}", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> addCredit(
			@PathVariable("username") String username,
			@PathVariable("amount") double amount) {
		Map<String, Object> result = new HashMap<String, Object>();
		Account account = accountService.updateBalance(username, amount);
		result.put("username", username);
		result.put("amount", account.getBalance());
		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
	}
}
