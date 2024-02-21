package com.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.dto.SignUp;
import com.security.entity.Role;
import com.security.entity.User;
import com.security.repository.UserRepository;

//import lombok.RequiredArgsConstructor;


@Service
//@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;



/**
	 * @param userRepository
	 * @param passwordEncoder
	 */
	public UserServiceImpl(UserRepository userRepository) {
//		super();
		this.userRepository = userRepository;
//		this.passwordEncoder = passwordEncoder;
	}




	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Find a user by the provided username using the userRepository
		Optional<User> user = userRepository.findByEmail(username);
		// If the user is found, return the user details
		if (user.isPresent()) {
			return user.get();
		} else {
			// If the user is not found, throw a UsernameNotFoundException
			throw new UsernameNotFoundException("User Not Found");
		}
	}




//
//
//
//	@Override
//	public ResponseEntity<User> saveUser(SignUp signup) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	
//	
//	
	
	
	

//	@Override
//	public UserDetailsService userDetailsService() {
//		// TODO Auto-generated method stub
//		return new UserDetailsService() {
//
//			@Override
//			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//				
//				return userRepository.findByEmail(username)
//						.orElseThrow(()->new UsernameNotFoundException("User Not Found"));
//			}
//			
//		};
//	}
	
//	
//	
////	@Override
//	public ResponseEntity<User>  saveUser(SignUp signup) {
//		User user =new User();
//		user.setEmail(signup.getEmail());
//		user.setLastname(signup.getLastname());
//		user.setRole(Role.User);
//		user.setUsername(signup.getUsername());
//		user.setPassword(passwordEncoder.encode(signup.getPassword()));
//		
//		return ResponseEntity.ok(userRepository.save(user)); 
//	}
//










}
