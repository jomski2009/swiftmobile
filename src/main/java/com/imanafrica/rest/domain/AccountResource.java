package com.imanafrica.rest.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.imanafrica.core.domain.Account;


@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown=true)
public class AccountResource extends ResourceSupport implements Serializable {

	private int accountid;
	private String name;
	private double balance;
	private double smsvalue;
			
	private Date creationdate;
	
	public AccountResource(){}
	public AccountResource(Account entity) {
		BeanUtils.copyProperties(entity, this);
	}

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
