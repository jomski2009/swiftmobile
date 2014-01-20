package com.imanafrica.core.domain;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@SuppressWarnings("serial")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "recipients")
public class Recipient extends EntityObject {

	@Id
	@GeneratedValue
	@Column(name="id")
	private int recipientid;
	private long cellnumber;
	private Date dateadded;

	@JsonBackReference
	@ManyToMany(mappedBy = "recipients", targetEntity = Sms.class)
	private Set<Sms> receivedsms = new HashSet<Sms>();

	@JsonBackReference
	@ManyToMany(mappedBy="members", targetEntity = Group.class)
	private Set<Group> groups;

	public int getRecipientid() {
		return recipientid;
	}

	public void setRecipientid(int id) {
		this.recipientid = id;
	}

	public long getCellnumber() {
		return cellnumber;
	}

	public void setCellnumber(long cellnumber) {
		this.cellnumber = cellnumber;
	}

	public Set<Sms> getReceivedsms() {
		return receivedsms;
	}

	public void setReceivedsms(Set<Sms> receivedsms) {
		this.receivedsms = receivedsms;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public Date getDateadded() {
		if (dateadded == null) {
			dateadded = new GregorianCalendar().getTime();
		}
		return dateadded;
	}

	public void setDateadded(Date dateadded) {
		if(dateadded==null){
			this.dateadded = new GregorianCalendar().getTime();
		}
		this.dateadded = dateadded;
	}

	@Override
	public String toString() {

		return "Recipient: Cellnumber - " + this.getCellnumber();
	}

}
