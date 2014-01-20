package com.imanafrica.rest.v1.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.imanafrica.core.domain.User;
import com.imanafrica.core.services.AccountService;
import com.imanafrica.core.services.UserService;
import com.imanafrica.rest.domain.AccountResource;
import com.imanafrica.rest.domain.assemblers.AccountResourceAssembler;
import com.imanafrica.rest.services.AccountRestService;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountsRestController {
	@Autowired
	UserService userService;

	@Autowired
	AccountRestService accountRestService;

	@Autowired
	AccountService accountService;

	@Autowired
	AccountResourceAssembler accountResourceAssembler;

	@RequestMapping(value = "{userid}/create", method = RequestMethod.POST)
	public ResponseEntity<AccountResource> createAccount(
			@PathVariable("userid") int userid,
			@RequestBody(required = true) AccountResource accountResource) {
		User user = userService.findUserById(userid);
		accountResource = accountRestService.createAccount(accountResource,
				user);

		accountResource.add(linkTo(UserRestController.class).slash("id")
				.slash(userid).withRel("owner"));

		return new ResponseEntity<AccountResource>(accountResource,
				HttpStatus.CREATED);
	}

	@RequestMapping("user/{userid}")
	public ResponseEntity<AccountResource> getAccountForUser(
			@PathVariable("userid") int userid) {
		AccountResource account = accountResourceAssembler
				.toResource(accountService.getAccountByUserUserid(userid));
		if(account == null){
			HttpHeaders headers = new HttpHeaders();
			headers.set("Account Error", "No account for user id " + userid + " found.");
			return new ResponseEntity<AccountResource>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<AccountResource>(account, HttpStatus.OK);
	}
	
	@RequestMapping("account/{accountid}")
	public ResponseEntity<AccountResource> getAccountById(@PathVariable("accountid") int accountid){
		AccountResource account = accountResourceAssembler.toResource(accountService.getAccountById(accountid));		
		return new ResponseEntity<AccountResource>(account, HttpStatus.OK);
	}
}
