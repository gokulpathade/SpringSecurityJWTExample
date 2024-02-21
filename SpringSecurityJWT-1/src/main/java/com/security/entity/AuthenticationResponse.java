package com.security.entity;

public class AuthenticationResponse {

	
	private String token;

	/**
	 * @param token
	 */
	public AuthenticationResponse(String token) {
//		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}
	
	
	
}
