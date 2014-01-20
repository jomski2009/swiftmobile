package com.imanafrica.core.services;

import java.util.List;
import java.util.Map;

import com.imanafrica.core.domain.Group;
import com.imanafrica.core.domain.Recipient;
import com.imanafrica.core.domain.User;

public interface GroupService {
	Group create(Group group);

	Group create(Group group, String username);

	List<Group> getGroupsForUser(User user);
	
	List<Group> getGroupsForUserByUserId(int userid);

	Map<String, Object> addRecipients(int groupid, List<Recipient> numbersToAdd);

	Group getGroupById(int groupid);

	Group create(Group group, int userid);

}
