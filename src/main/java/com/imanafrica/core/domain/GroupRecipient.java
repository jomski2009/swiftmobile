package com.imanafrica.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//@SuppressWarnings("serial")
//@Entity
//@Table(name = "groups_recipients")
public class GroupRecipient extends EntityObject {
	private Group group;
	private Recipient recipient;

	@Column(name = "value1")
	private String firstvalue;
	
	@Column(name = "value2")
	private String secondvalue;
	
	@Column(name = "value3")
	private String thirdvalue;
	
	@Column(name = "value4")
	private String fourthvalue;
	
	@Column(name = "value5")
	private String fifthvalue;

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Recipient getRecipient() {
		return recipient;
	}

	public void setRecipient(Recipient recipient) {
		this.recipient = recipient;
	}

	public String getFirstvalue() {
		return firstvalue;
	}

	public void setFirstvalue(String firstvalue) {
		this.firstvalue = firstvalue;
	}

	public String getSecondvalue() {
		return secondvalue;
	}

	public void setSecondvalue(String secondvalue) {
		this.secondvalue = secondvalue;
	}

	public String getThirdvalue() {
		return thirdvalue;
	}

	public void setThirdvalue(String thirdvalue) {
		this.thirdvalue = thirdvalue;
	}

	public String getFourthvalue() {
		return fourthvalue;
	}

	public void setFourthvalue(String fourthvalue) {
		this.fourthvalue = fourthvalue;
	}

	public String getFifthvalue() {
		return fifthvalue;
	}

	public void setFifthvalue(String fifthvalue) {
		this.fifthvalue = fifthvalue;
	}

}
