package com.imanafrica.rest.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.imanafrica.core.domain.Recipient;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipientResource extends ResourceSupport implements Serializable {

	private int recipientid;
	private long cellnumber;
	private Date dateadded;

	private String firstvalue;
	private String secondvalue;
	private String thirdvalue;
	private String fourthvalue;
	private String fifthvalue;

	public RecipientResource() {
	}

	public RecipientResource(Recipient entity) {
		BeanUtils.copyProperties(entity, this);
	}

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

	public Date getDateadded() {
		if (dateadded == null) {
			dateadded = new GregorianCalendar().getTime();
		}
		return dateadded;
	}

	public void setDateadded(Date dateadded) {
		if (dateadded == null) {
			this.dateadded = new GregorianCalendar().getTime();
		}
		this.dateadded = dateadded;
	}

}
