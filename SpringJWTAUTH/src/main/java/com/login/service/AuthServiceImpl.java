package com.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.login.dto.LoginDto;
import com.login.util.JwtTokenProvider;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {


@Autowired
	private final AuthenticationManager authenticationManager;
    
@Autowired
    private final JwtTokenProvider jwtTokenProvider;
    
    
    
    
    
//    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
//		super();
//		this.authenticationManager = authenticationManager;
//		this.jwtTokenProvider = jwtTokenProvider;
//	}
//    
//    
    @Override
    public String login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return jwtTokenProvider.generateToken(authentication);
        } catch (Exception e) {
            // Handle authentication failure, for example, throw a custom exception or log the error
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
    }
}
