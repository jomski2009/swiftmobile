package com.imanafrica.rest.domain;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.imanafrica.core.domain.User;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResource extends ResourceSupport implements Serializable {
	private int userid;
	private String firstname;
	private String lastname;
	private String username;
	private String email;
	private long cellnumber;
	private String password;

	public UserResource() {
	}

	public UserResource(User user) {
		BeanUtils.copyProperties(user, this);
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return this.getFirstname() + " " + this.getLastname();
	}

}
