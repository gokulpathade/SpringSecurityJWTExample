package com.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

	
	
	
	
//	   @PreAuthorize("hasRole('Admin')")
	    @GetMapping("/admin")
	    public ResponseEntity<String> helloAdmin(){
	        return ResponseEntity.ok("Hello Admin");
	    }

//	    @PreAuthorize("hasRole('User')")
	    @GetMapping("/user")
	    public ResponseEntity<String> helloUser(){
	        return ResponseEntity.ok("Hello User");
	    }
	
//	    @PreAuthorize("hasRole('Manager')")
	    @GetMapping("/manager")
	    public ResponseEntity<String> helloManager(){
	        return ResponseEntity.ok("Hello Manager");
	    }
	
	
	
}
