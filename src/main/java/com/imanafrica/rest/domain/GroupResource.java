package com.imanafrica.rest.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.ResourceSupport;

import com.imanafrica.core.domain.Group;

@SuppressWarnings("serial")
public class GroupResource extends ResourceSupport implements Serializable {

	private int groupid;
	private String name;
	private String description;
	private Date creationdate;

	public GroupResource() {
	}

	public GroupResource(Group entity) {
		BeanUtils.copyProperties(entity, this);
	}

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int id) {
		this.groupid = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

}
