package com.imanafrica.core.exceptions;

public class InvalidCustomerException extends Exception {
	private static final long serialVersionUID = 5885227960230690026L;

	@Override
	public String getMessage() {
		return "The customer data is invalid";
	}

}
