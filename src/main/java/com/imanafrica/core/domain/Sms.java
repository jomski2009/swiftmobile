package com.imanafrica.core.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@SuppressWarnings("serial")
@Entity
@Table(name="sms")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Sms extends EntityObject {
    
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="message")
	private String text;
	
	private String messageid;
	private String type;
	private Date datesent;
	
	@JsonManagedReference
	@ManyToMany(targetEntity=Recipient.class)
	@JoinTable(name="smsrecipients", joinColumns={ @JoinColumn(name="smsid") }, inverseJoinColumns={ @JoinColumn(name="recipientsid") })	
	private Set<Recipient> recipients = new HashSet<Recipient>();
	
	@JsonManagedReference
	@OneToMany(mappedBy="targetSms", targetEntity=SmsResponse.class)	
	private Set<SmsResponse> responses=new HashSet<SmsResponse>();
	
	@JsonManagedReference
	@OneToMany(mappedBy="sms", targetEntity=DeliveryReport.class, fetch=FetchType.LAZY)
	private Set<DeliveryReport> deliveryReports = new HashSet<DeliveryReport>();
	
	@ManyToOne
	@JoinColumn(name="userid", referencedColumnName="id")
	private User user;
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public String getMessageid() {
		return messageid;
	}

	public void setEventid(String eventid) {
		this.messageid = eventid;
	}

	public void setRecipients(Set<Recipient> recipients) {
		this.recipients = recipients;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<Recipient> getRecipients() {
		return recipients;
	}


	public Date getSentdate() {
		return datesent;
	}

	public void setSentdate(Date sentdate) {
		this.datesent = sentdate;
	}

	public Set<SmsResponse> getResponses() {
		return responses;
	}

	public void setResponses(Set<SmsResponse> responses) {
		this.responses = responses;
	}

	public Set<DeliveryReport> getDeliveryReports() {
		return deliveryReports;
	}

	public void setDeliveryReports(Set<DeliveryReport> deliveryReports) {
		this.deliveryReports = deliveryReports;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


}
