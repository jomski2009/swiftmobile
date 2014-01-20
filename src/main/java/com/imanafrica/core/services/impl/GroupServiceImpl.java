package com.imanafrica.core.services.impl;

import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.imanafrica.core.dao.GroupDao;
import com.imanafrica.core.dao.RecipientDao;
import com.imanafrica.core.dao.UserDao;
import com.imanafrica.core.domain.Group;
import com.imanafrica.core.domain.Recipient;
import com.imanafrica.core.domain.User;
import com.imanafrica.core.services.GroupService;

@Repository
public class GroupServiceImpl implements GroupService {
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	GroupDao groupDao;

	@Autowired
	UserDao userDao;

	@Autowired
	RecipientDao recipientDao;

	@Override
	public Group create(Group group) {
		group = groupDao.save(group);
		return group;
	}

	@Override
	public List<Group> getGroupsForUser(User user) {
		return groupDao.findByOwner(user);
	}

	@Override
	public Group create(Group group, String username) {
		User user = userDao.findByUsername(username);
		group.setOwner(user);
		group = groupDao.save(group);
		return group;
	}

	@Override
	public Map<String, Object> addRecipients(int groupid,
			List<Recipient> recipients) {
		Map<String, Object> results = new HashMap<String, Object>();
		Group group = getGroupById(groupid);

		Iterator<Recipient> itr = recipients.iterator();

		while (itr.hasNext()) {
			Recipient oldrec = itr.next();
			Recipient newrec = saveOrUpdate(oldrec);
			group.getMembers().add(newrec);
		}

		group = groupDao.saveAndFlush(group);
		// Assuming the operation completed...
		results.put("response", recipients.size() +" numbers added to " + group.getName());
		return results;
	}

	@Override
	public Group getGroupById(int groupid) {
		return groupDao.findOne(groupid);
	}

	private Recipient saveOrUpdate(Recipient oldrec) {
		if (recipientDao.findByCellnumber(oldrec.getCellnumber()) != null) {
			log.info("A recipient object was found: "
					+ recipientDao.findByCellnumber(oldrec.getCellnumber())
							.toString());
			return recipientDao.findByCellnumber(oldrec.getCellnumber());
		} else {
			oldrec.setDateadded(new GregorianCalendar().getTime());
			return recipientDao.save(oldrec);
		}
	}

	@Override
	public List<Group> getGroupsForUserByUserId(int userid) {
		return groupDao.findByOwnerUserid(userid);
	}

	@Override
	public Group create(Group group, int userid) {
		User user = userDao.findOne(userid);
		group.setOwner(user);
		group.setCreationdate(new GregorianCalendar().getTime());
		group = groupDao.save(group);
		return group;	
	}

}
