package com.imanafrica.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SuppressWarnings("serial")
@Entity
@Table(name = "accounts")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Account extends EntityObject {

	@Id
	@GeneratedValue
	@Column(name="id")
	private int accountid;
	private String name;
	private double balance = 0.00;
	private double smsvalue;
	
	@JsonBackReference
	@ManyToOne(targetEntity=User.class)	
	@JoinColumns({ @JoinColumn(name="user_id", referencedColumnName="id", nullable=false) })	
	private User user;
	
	@Column(name="creationdate", nullable=false)		
	private Date creationdate;
	
	public int getAccountid() {
		return accountid;
	}

	public Date getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public void setAccountid(int id) {
		this.accountid = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSmsvalue() {
		return smsvalue;
	}

	public void setSmsvalue(double smsvalue) {
		this.smsvalue = smsvalue;
	}

	public void updateBalance(double amount) {
		this.balance = this.balance + amount;
	}
	

}
