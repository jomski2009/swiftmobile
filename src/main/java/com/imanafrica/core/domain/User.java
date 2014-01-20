package com.imanafrica.core.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Required;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@SuppressWarnings("serial")
@Entity
@Table(name = "users")
@JsonIgnoreProperties(ignoreUnknown=true)
public class User extends EntityObject {
	@Id
	@GeneratedValue
	@Column(name="id")
	private int userid;

	private String username;

	private String firstname;

	private String lastname;

	private String email;

	private long cellnumber;

	private String password;

	private Date creationdate;
	private boolean enabled;

	@JsonManagedReference
	@OneToMany(mappedBy = "user", targetEntity = Account.class)
	private Set<Account> accounts = new HashSet<Account>();

	@JsonManagedReference
	@OneToMany(mappedBy = "owner", targetEntity = Group.class)
	private Set<Group> groups = new HashSet<Group>();

	public int getUserid() {
		return userid;
	}

	public void setUserid(int id) {
		this.userid = id;
	}

	@Required
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getCellnumber() {
		return cellnumber;
	}

	public void setCellnumber(long cellnumber) {
		this.cellnumber = cellnumber;
	}

	public Date getCreateddate() {
		return creationdate;
	}

	public void setCreateddate(Date createddate) {
		this.creationdate = createddate;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	// Convenience methods to maintain circular references... (Gleaned from
	// Spring persistence with Hibernate)
	public boolean addAccountToUser(Account account) {
		account.setUser(this);
		return this.getAccounts().add(account);
	}

	public boolean addGroupToUser(Group group) {
		group.setOwner(this);
		return this.getGroups().add(group);
	}

	@Override
	public String toString() {
		return "Customer: " + getFirstname() + " " + getLastname();
	}

}
