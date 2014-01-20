package com.imanafrica.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class RecipientDTO {
	private long cellnumber;
	private int id;

	public long getCellnumber() {
		return cellnumber;
	}

	public void setCellnumber(long cellnumber) {
		this.cellnumber = cellnumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
