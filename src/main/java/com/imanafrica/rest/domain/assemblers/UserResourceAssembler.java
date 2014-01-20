package com.imanafrica.rest.domain.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.imanafrica.core.domain.User;
import com.imanafrica.rest.domain.UserResource;
import com.imanafrica.rest.v1.controller.AccountsRestController;
import com.imanafrica.rest.v1.controller.GroupRestController;
import com.imanafrica.rest.v1.controller.UserRestController;

@Component
public class UserResourceAssembler extends
		ResourceAssemblerSupport<User, UserResource> {

	public UserResourceAssembler() {
		super(UserRestController.class, UserResource.class);
	}

	@Override
	protected UserResource instantiateResource(User entity) {
		return new UserResource(entity);
	}

	@Override
	public UserResource toResource(User entity) {
		UserResource resource = new UserResource(entity);
		resource.setPassword("HIDDEN");
		resource.add(linkTo(AccountsRestController.class).slash("user")
				.slash(entity.getUserid()).withRel("account"));
		resource.add(linkTo(GroupRestController.class)
				.slash(entity.getUserid()).slash("groups").withRel("groups"));
		return resource;
	}

}
