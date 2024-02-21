package com.security.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SignUp {
	
	private String username;
	private String lastname;

	private String password;
	private String email;
}
