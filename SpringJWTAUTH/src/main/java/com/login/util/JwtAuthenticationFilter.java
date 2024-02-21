package com.login.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;



//Execute Before Executing Spring Security Filters
//Validate the JWT Token and Provides user details to Spring Security for Authentication
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
 private  JwtTokenProvider jwtTokenProvider;

	@Autowired 
 private  UserDetailsService userDetailsService;


/**
 * This method filters the incoming HTTP request and response.
 * It extracts the JWT token from the request, validates it, and sets the user authentication details in the security context.
 * 
 * @param  request       the HTTP request
 * @param  response      the HTTP response
 * @param  filterChain   the filter chain
 * @throws ServletException  if a servlet-specific error occurs
 * @throws IOException       if an I/O error occurs
 * @return               no return value
 */
@Override
protected void doFilterInternal(HttpServletRequest request,
                                HttpServletResponse response,
                                FilterChain filterChain) throws ServletException, IOException {

    // Get JWT token from HTTP request
    String token = getTokenFromRequest(request);

    // Validate Token
    if(StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)){
        // get username from token
        String username = jwtTokenProvider.getUsername(token);

        // Load user details by username
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Create authentication token
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.getAuthorities()
        );

        // Set authentication token details
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Set the authentication token in the security context
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    // Continue with the filter chain
    filterChain.doFilter(request, response);
}






/**
* Retrieves the token from the HTTP servlet request.
*
* @param  request   the HTTP servlet request
* @return          the token extracted from the request, or null if not found
*/
private String getTokenFromRequest(HttpServletRequest request){
    // Get the token from the Authorization header in the request
    String bearerToken = request.getHeader("Authorization");
 
    // Check if the bearer token is not empty and starts with "Bearer "
    if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
        // Extract the token from the bearer token string and return it
        return bearerToken.substring(7);
    }
 
    // Return null if no valid token is found in the request
    return null;
  }

//@Override
//protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//		throws ServletException, IOException {
//	// TODO Auto-generated method stub
//	
//}
}