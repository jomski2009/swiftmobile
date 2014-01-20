package com.imanafrica.rest.domain.assemblers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.imanafrica.core.domain.Group;
import com.imanafrica.rest.domain.GroupResource;
import com.imanafrica.rest.v1.controller.GroupRestController;
import com.imanafrica.rest.v1.controller.UserRestController;

@Component
public class GroupResourceAssembler extends
		ResourceAssemblerSupport<Group, GroupResource> {

	public GroupResourceAssembler() {
		super(GroupRestController.class, GroupResource.class);
	}

	@Override
	protected GroupResource instantiateResource(Group entity) {
		return new GroupResource(entity);
	}

	@Override
	public GroupResource toResource(Group group) {
		GroupResource resource = new GroupResource(group);
		resource.add(linkTo(GroupRestController.class).slash("group").slash(group.getGroupid()).withSelfRel());
		resource.add(linkTo(UserRestController.class).slash("id")
				.slash(group.getOwner().getUserid()).withRel("owner"));
		resource.add(linkTo(GroupRestController.class).slash("group")
				.slash(group.getGroupid()).slash("members").withRel("members"));
		return resource;
	}

}
