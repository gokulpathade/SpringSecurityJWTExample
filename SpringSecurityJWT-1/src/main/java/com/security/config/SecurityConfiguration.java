 package com.security.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.security.entity.Role;
import com.security.service.UserServiceImpl;

;

@Configuration
@EnableWebSecurity
//@RequiredArgsConstructor

public class SecurityConfiguration extends WebSecurityConfiguration {

	
	





	private  final  JWTAuthenticationFilter jwtAuthenticationFilter;
	
//	private final UserService userService;
	private final UserServiceImpl userServiceImpl;
	
	

	/**
	 * @param jwtAuthenticationFilter
	 * @param userDetailsService
	 */
	public SecurityConfiguration(JWTAuthenticationFilter jwtAuthenticationFilter,
			UserServiceImpl userServiceImpl) {
		super();
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.userServiceImpl = userServiceImpl;
	}



	
	
	
	
//	 protected void configure(HttpSecurity http) throws Exception {
//	        http.csrf().disable()
//	            .authorizeRequests(request -> request
//	                .requestMatchers("/api/**").permitAll()
//	                .requestMatchers("/api/Admin").hasAnyAuthority(Role.Admin.name())
//	                .requestMatchers("/api/User").hasAnyAuthority(Role.User.name())
//	                .requestMatchers("/api/Manager").hasAnyAuthority(Role.Manager.name())
//	                .anyRequest().authenticated())
//	            .sessionManagement(manager -> manager
//	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//	                 	.authenticationProvider(authenticationProvider()).
//	                 	addFilterBefore(jwtAuthenticationFilter, 
//	                 			UsernamePasswordAuthenticationFilter.class)
//	    		   }

	
	
	
	
//	
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	    http.csrf(AbstractHttpConfigurer::disable)
//	        .authorizeHttpRequests(request -> request
//	            .requestMatchers("/api/**", "/register/**").permitAll()
//	            .anyRequest().authenticated()
//	        )
//	        .userDetailsService(userServiceImpl)
//	        .sessionManagement(session -> session
//	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//	        )
//	        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//	        .exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
//	            // Log or print details about the authentication failure
//	            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
//	        });
//
//	    return http.build();
//	}
//
//	
	
	









/**
 * Configures the security filter chain for the application.
 *
 * @param  http	the HttpSecurity object to be configured
 * @return         	the configured SecurityFilterChain object
 */
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        // Disable CSRF protection
	        .csrf().disable()
	        // Authorize requests
	        .authorizeHttpRequests(authorize ->
	            authorize
	                // Permit all requests to /register/**
	                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
	                .requestMatchers("/register/**").permitAll()
	                .requestMatchers("/api/**").permitAll()
//	                .requestMatchers("/api/**").hasAuthority(Role.Admin.name())
//	                .requestMatchers("/api/user/**").hasAnyAuthority(Role.User.name())
//	                .requestMatchers("/api/manager/**").hasAnyAuthority(Role.Manager.name())
	                // Authenticate any other request
	                .anyRequest().authenticated()
	        )
	        // Set session management to be stateless
	        .sessionManagement(session -> session
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        // Add JWT authentication filter before UsernamePasswordAuthenticationFilter
	        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
	        // Handle unauthorized requests with 401 status code
	        .exceptionHandling(exceptionHandling -> exceptionHandling
	            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

	    // Build and return the configured HttpSecurity object
	    return http.build();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


//
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		
//http.csrf(AbstractHttpConfigurer :: disable)
//.authorizeHttpRequests(
//		request->request.requestMatchers("/api/**", "/register/**")
//		.permitAll()
////		.requestMatchers("/api/Admin").hasAnyAuthority(Role.Admin.name())
////		.requestMatchers("/api/User").hasAnyAuthority(Role.User.name())
////		.requestMatchers("/api/Manager").hasAnyAuthority(Role.Manager.name())
//		.anyRequest()
//		.authenticated()
//		).	userDetailsService(userServiceImpl)
//.sessionManagement(session->session
//		.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
////.build();
//
//
//
////.sessionManagement(manager -> manager
////        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
////         	.authenticationProvider(authenticationProvider()).
////         	addFilterBefore(jwtAuthenticationFilter, 
////         			UsernamePasswordAuthenticationFilter.class);
//
////.sessionManagement(manager->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
////		.authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)	
////		);
//
//return http.build();
//
//	}

	
	
//	(manager->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//			.authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)	
//			);	

//	private Object AuthenticationProvider() {
//		// TODO Auto-generated method stub
//		return null;
//	}



//
//	@Bean
//	public AuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//		authenticationProvider.setUserDetailsService((UserDetailsService) userDetailsService.loadUserByUsername(null));
//		authenticationProvider.setPasswordEncoder(passwordEncoder());
//		return authenticationProvider;
//	}

	
	


	/**
	 * A description of the entire Java function.
	 *
	 * @return         	description of return value
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	


		/**
	 * A description of the entire Java function.
	 *
	 * @param  config	description of parameter
	 * @return         	description of return value
	 */
	@Bean
	public AuthenticationManager  authticationManager(AuthenticationConfiguration config) 
			throws Exception {
		
		return config.getAuthenticationManager();
		
	}
	
	





}
