package com.imanafrica.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name="deliveryreports")
public class DeliveryReport extends EntityObject {

		
	@Id	
	@GeneratedValue	
	private long id;
	
	@Column(name="messageid", nullable=true, length=255)	
	private String messageid;
	
	@Column(name="sentdate", nullable=false)	
	@Temporal(TemporalType.DATE)	
	private java.util.Date sentdate;
	
	@Column(name="donedate", nullable=false)	
	@Temporal(TemporalType.DATE)	
	private java.util.Date donedate;
	
	@Column(name="status", nullable=false, length=100)	
	private String status;
	
	@Column(name="gsmerror", nullable=false, length=4)	
	private int gsmerror;
	
	@ManyToOne(targetEntity=Sms.class, fetch=FetchType.LAZY)	
	@JoinColumns({ @JoinColumn(name="smsid", referencedColumnName="id", nullable=false) })		
	private Sms sms;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessageid() {
		return messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public java.util.Date getSentdate() {
		return sentdate;
	}

	public void setSentdate(java.util.Date sentdate) {
		this.sentdate = sentdate;
	}

	public java.util.Date getDonedate() {
		return donedate;
	}

	public void setDonedate(java.util.Date donedate) {
		this.donedate = donedate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getGsmerror() {
		return gsmerror;
	}

	public void setGsmerror(int gsmerror) {
		this.gsmerror = gsmerror;
	}

	public Sms getSms() {
		return sms;
	}

	public void setSms(Sms sms) {
		this.sms = sms;
	}
}
