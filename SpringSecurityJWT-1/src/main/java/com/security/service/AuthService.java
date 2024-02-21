package com.security.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.dto.SignUp;
import com.security.entity.AuthenticationResponse;
import com.security.entity.Role;
import com.security.entity.User;
import com.security.repository.UserRepository;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JWTServiceImpl jwtService;
	private final AuthenticationManager authManager;




	/**
	 * @param userRepository
	 * @param passwordEncoder
	 * @param jwtService
	 * @param authResponse
	 */
	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTServiceImpl jwtService,
			AuthenticationManager authManager) {

		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authManager = authManager;
	}






	/**
	 * Save user information to the database and return authentication response.
	 *
	 * @param  signup  user object containing signup information
	 * @return        authentication response with token
	 */

	public AuthenticationResponse saveUser(User signup) {
		User user = new User();
		user.setEmail(signup.getEmail());
		user.setLastname(signup.getLastname());
		user.setRole(signup.getRole());
		user.setUsername(signup.getUsername());
		user.setPassword(passwordEncoder.encode(signup.getPassword()));
		user = userRepository.save(user);
//		return ResponseEntity.ok(userRepository.save(user)); 
		String token = jwtService.generateToken(user);
		return new AuthenticationResponse(token);
	}








		/**
	 * Authenticates the user and generates an authentication token.
	 *
	 * @param  signin  the user information for authentication
	 * @return        an authentication response containing the generated token
	 */
	public AuthenticationResponse authenticate(User signin) {
		authManager.authenticate(new UsernamePasswordAuthenticationToken(signin.getEmail(), signin.getPassword()));

		User user = userRepository.findByEmail(signin.getEmail()).orElseThrow();
		String token = jwtService.generateToken(user);

		return new AuthenticationResponse(token);
	}

}
