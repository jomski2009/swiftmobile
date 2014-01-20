package com.imanafrica.rest.v1.controller;

import org.springframework.security.crypto.codec.Base64;

public class SwiftMobileUtils {

	public static String getUsername(String authString){
		authString = authString.substring(6);
		byte[] values = Base64.decode(authString.getBytes());
		String[] userNamePassword = new String(values).split(":");
		String username = userNamePassword[0];
		
		return username;
	}
}
