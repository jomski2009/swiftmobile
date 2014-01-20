package com.imanafrica.rest.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imanafrica.core.domain.Group;
import com.imanafrica.core.domain.Recipient;
import com.imanafrica.core.services.GroupService;
import com.imanafrica.rest.domain.GroupResource;
import com.imanafrica.rest.domain.RecipientResource;

@Service
public class GroupRestService {
	@Autowired
	GroupService groupService;

	// RestGroup create(RestGroup group);

	// RestGroup create(RestGroup group, String username);

	// List<RestGroup> getGroupsForUser(User user);

	public List<GroupResource> getGroupsForUserByUserId(int userid) {
		List<Group> coregroups = groupService.getGroupsForUserByUserId(userid);
		List<GroupResource> groups = new ArrayList<GroupResource>();
		for (Group g : coregroups) {
			GroupResource rg = new GroupResource();
			BeanUtils.copyProperties(g, rg);
			groups.add(rg);
		}
		return groups;
	}

	public Map<String, Object> addToGroup(int groupid, List<RecipientResource> numbers) {
		List<Recipient> coreRecipients = new ArrayList<Recipient>();
		for (RecipientResource rr : numbers) {
			Recipient r = new Recipient();
			BeanUtils.copyProperties(rr, r);
			coreRecipients.add(r);
		}
		Map<String, Object> result = groupService.addRecipients(groupid, coreRecipients);
		return result;
	}

	public GroupResource createGroup(int userid, GroupResource group) {
		Group coregroup = new Group();
		BeanUtils.copyProperties(group, coregroup);
		coregroup = groupService.create(coregroup, userid);
		BeanUtils.copyProperties(coregroup, group);
		return group;
	}

	// Map<String, Object> addRecipients(int groupid, List<Recipient>
	// numbersToAdd);

	// RestGroup getGroupById(int groupid);
}
