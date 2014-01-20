package com.imanafrica.rest.domain.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.imanafrica.core.domain.Account;
import com.imanafrica.rest.domain.AccountResource;
import com.imanafrica.rest.v1.controller.AccountsRestController;
import com.imanafrica.rest.v1.controller.UserRestController;

@Component
public class AccountResourceAssembler extends
		ResourceAssemblerSupport<Account, AccountResource> {

	public AccountResourceAssembler() {
		super(AccountsRestController.class, AccountResource.class);
	}

	@Override
	protected AccountResource instantiateResource(Account entity) {
		return new AccountResource(entity);
	}

	@Override
	public AccountResource toResource(Account entity) {
		AccountResource resource = new AccountResource(entity);
		resource.add(linkTo(AccountsRestController.class).slash("account").slash(entity.getAccountid()).withSelfRel());
		resource.add(linkTo(UserRestController.class).slash("id")
				.slash(entity.getUser().getUserid()).withRel("owner"));

		return resource;
	}

}
