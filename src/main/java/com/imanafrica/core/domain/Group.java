package com.imanafrica.core.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@SuppressWarnings("serial")
@Entity
@Table(name = "groups")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Group extends EntityObject {

	@Id
	@GeneratedValue
	@Column(name="id")
	private int groupid;
	private String name;
	private String description;
	private Date creationdate;

	@JsonBackReference
	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "ownerid", referencedColumnName = "id", nullable = false) })
	private User owner;

	@JsonManagedReference
	@ManyToMany(targetEntity = Recipient.class)
	@JoinTable(name = "groups_recipients", joinColumns = { @JoinColumn(name = "groupsid") }, inverseJoinColumns = { @JoinColumn(name = "recipientsid") })
	private Set<Recipient> members;
	

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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Set<Recipient> getMembers() {
		return members;
	}

	public void setMembers(Set<Recipient> members) {
		this.members = members;
	}

}
