package com.imanafrica.rest.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;




@JsonIgnoreProperties(ignoreUnknown=true)
public class SmsDTO{
	private String text;
	private String messageid;
	private String type;
	
	private List<RecipientDTO> recipients;

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public String getMessageid() {
		return messageid;
	}

	public void setEventid(String eventid) {
		this.messageid = eventid;
	}

	public void setRecipients(List<RecipientDTO> recipients) {
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

	public List<RecipientDTO> getRecipients() {
		return recipients;
	}

}
