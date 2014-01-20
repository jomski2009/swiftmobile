package com.imanafrica.core.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@SuppressWarnings("serial")
@Entity
@Table(name="smsresponses")
public class SmsResponse extends EntityObject {
	@Id
	@GeneratedValue
	private long id;
	private int status;
	private String messageid;
	private long destination;
	
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="smsid", referencedColumnName="id")
	private Sms targetSms;
	

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getDestination() {
		return destination;
	}

	public void setDestination(long destination) {
		this.destination = destination;
	}

	public Sms getTargetSms() {
		return targetSms;
	}

	public void setTargetSms(Sms targetSms) {
		this.targetSms = targetSms;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\"response\":{");
		builder.append("\"status\":" + getStatus() + ",");
		builder.append("\"messageId\":\"" + getMessageid() + "\",");
		builder.append("\"destination\":+" + getDestination());
		builder.append("}");

		return builder.toString();
	}
}
