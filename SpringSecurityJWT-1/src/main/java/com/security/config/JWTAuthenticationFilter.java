package com.security.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.service.JWTServiceImpl;
//import com.security.service.UserService;
import com.security.service.UserServiceImpl;

//import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;

@Component
//@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	private final UserServiceImpl userServiceDetails;

	private final JWTServiceImpl jwtService;

	/**
	 * @param jwtService
	 * @param userServiceDetails
	 */
	public JWTAuthenticationFilter(UserServiceImpl userServiceDetails, JWTServiceImpl jwtService) {
//		super();

		this.userServiceDetails = userServiceDetails;
		this.jwtService = jwtService;
	}



	
	/**
 * This method is an implementation of the doFilterInternal method of the Filter interface.
 * It checks for the presence of an Authorization header in the HTTP request, and if it's not present or does not start with "Bearer ",
 * it proceeds to the next filter in the chain.
 * If the Authorization header is present and starts with "Bearer ", it extracts the token and then attempts to extract the username from the token.
 * If the username is not null and the current security context does not have an authenticated user, it loads the user details using the username,
 * validates the token, creates an authentication token, sets the authentication details, and sets it in the security context.
 * Finally, it proceeds to the next filter in the chain.
 *
 * @param request      the HTTP servlet request
 * @param response     the HTTP servlet response
 * @param filterChain  the filter chain
 * @throws ServletException  if a servlet-specific error occurs
 * @throws IOException       if an I/O error occurs
 */


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);
			String username = jwtService.extractUsername(token);
			if (username != null || SecurityContextHolder.getContext().getAuthentication() != null) {
				UserDetails userDetails = userServiceDetails.loadUserByUsername(username);
				if (jwtService.isValid(token, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
		}
		filterChain.doFilter(request, response);
	}



	

}

























//	 String authHeader= request.getHeader("Authorization");
//// String Jwt;
//// String userEmail;
//if(StringUtils.isEmpty(authHeader)  ||  !org.apache.commons.lang3.StringUtils.startsWith(authHeader, "Bearer")) {
//	filterChain.doFilter(request, response);
//	return;
//}
//Jwt= authHeader.substring(7);
//userEmail = jwtService.extractUserName(Jwt);
//
//if(StringUtils.isNotEmpty(userEmail)&& SecurityContextHolder.getContext().getAuthentication()== null) {
////	UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
////	UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
//	UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
//
//	
//	if (  jwtService.isTokenValid (Jwt, userDetails) )   {
//		SecurityContext securityContext =SecurityContextHolder.createEmptyContext();
//		UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(userDetails, null,  userDetails.getAuthorities());
//
//		token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//		securityContext.setAuthentication(token);
//		SecurityContextHolder.setContext(securityContext);
//		
//		
//	}
//}
//      filterChain.doFilter(request, response);
//}


