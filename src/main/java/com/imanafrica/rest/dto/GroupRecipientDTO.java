package com.imanafrica.rest.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.imanafrica.core.domain.Recipient;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupRecipientDTO {
	private int groupid;
	private List<Recipient> recipients;

	public int getGroupid() {
		return groupid;
	}

	public void setGroupid(int groupid) {
		this.groupid = groupid;
	}

	public List<Recipient> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<Recipient> recipients) {
		this.recipients = recipients;
	}

}
