package com.imanafrica.rest.v1.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.imanafrica.core.domain.User;
import com.imanafrica.core.services.GroupService;
import com.imanafrica.core.services.SmsService;
import com.imanafrica.core.services.UserService;
import com.imanafrica.rest.domain.UserResource;
import com.imanafrica.rest.domain.assemblers.UserResourceAssembler;

@RestController
@RequestMapping(value = "/api/v1/user", produces = { "application/json" })
public class UserRestController {
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	UserService userService;

	@Autowired
	SmsService smsService;

	@Autowired
	GroupService groupService;

	@Autowired
	UserResourceAssembler userResourceAssembler;

	@RequestMapping("create")
	@ResponseBody
	public ResponseEntity<Void> create(
			@RequestBody UserResource userResource) {
		User user = new User();
		BeanUtils.copyProperties(userResource, user);
		user = userService.createUser(user);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(linkTo(UserRestController.class).slash("id").slash(user.getUserid()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<UserResource>> getAllUsers() {
		List<UserResource> userResources = userResourceAssembler.toResources(userService.findAll());
		return new ResponseEntity<List<UserResource>>(userResources,
				HttpStatus.OK);
	}

	@RequestMapping(value = "username/{username}", method = RequestMethod.GET)
	@ResponseBody
	public List<UserResource> getUserByUsername(@PathVariable String username) {
		List<UserResource> users = userResourceAssembler.toResources(userService.findUserByUsername(username));
		return users;
	}

	@RequestMapping("id/{id}")
	@ResponseBody
	public ResponseEntity<UserResource> getUserById(@PathVariable int id) {
		return new ResponseEntity<UserResource>(
				userResourceAssembler.toResource(userService.findUserById(id)),
				HttpStatus.OK);
	}

	@RequestMapping("credits")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> getCredits() {
		double credits = smsService.getCredits();
		Map<String, Object> results = new HashMap<String, Object>();
		results.put("credits", credits);
		results.put("sms credits", Math.round(credits / 0.18));

		return new ResponseEntity<Map<String, Object>>(results, HttpStatus.OK);
	}

}
