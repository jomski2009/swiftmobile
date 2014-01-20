package com.imanafrica.core.domain.proxy;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Required;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "users")
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserProxy {
	@Id
	@GeneratedValue
	private int id;

	private String username;


	private String firstname;


	private String lastname;

	private String email;


	private long cellnumber;


	@Column(name = "creationdate", nullable = false)
	private Date createddate;
	private boolean userenabled;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public boolean isUserenabled() {
		return userenabled;
	}

	public void setUserenabled(boolean userenabled) {
		this.userenabled = userenabled;
	}


	@Override
	public String toString() {
		return "Customer: " + getFirstname() + " " + getLastname();
	}

}