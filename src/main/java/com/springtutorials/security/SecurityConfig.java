package com.springtutorials.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springtutorials.serviceImpl.CustomUserdetailsService;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	private final CustomUserdetailsService customUserdetailsService;
	private final JwtAuthFilter jwtAuthFilter;
	
	public SecurityConfig(CustomUserdetailsService customUserdetailsService,JwtAuthFilter jwtAuthFilter) {
		this.customUserdetailsService = customUserdetailsService;
		this.jwtAuthFilter = jwtAuthFilter;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		System.out.println("security filter chain");
		
		httpSecurity
		            .csrf(csrf->csrf.disable())
		            .authorizeHttpRequests(request->request
		            		.requestMatchers(
		                            "/swagger-ui/**",
		                            "/swagger-ui.html",
		                            "/v3/api-docs/**",
		                            "/api-docs/**"
		                        ).permitAll()
		            		.requestMatchers("/api/auth/login").permitAll()
		            		.requestMatchers("/api/admin/**").hasRole("ADMIN")
		            		.requestMatchers("/api/products/**").permitAll()
		            		.requestMatchers("/api/cart/**").permitAll()
		            		.requestMatchers("/api/users/**","/api/orders/**").permitAll()
		            		.requestMatchers("/api/users/me","/api/users/me/update").authenticated()
		            		.requestMatchers("/api/users/register").permitAll()
		            		 .anyRequest().authenticated()
		            		 )
		                    
		            .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		            .authenticationProvider(authenticationProvider())
		            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
		            
		            
		return httpSecurity.build();
	}
	
	
	 @Bean
	 public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder(); 
	 }
	 
	 @Bean
	 public AuthenticationProvider authenticationProvider() {
		 DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		 daoAuthenticationProvider.setUserDetailsService(customUserdetailsService);
		 daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		 
		 return daoAuthenticationProvider;
	 }
	 
	 @Bean
	 public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		 return authenticationConfiguration.getAuthenticationManager();
	 }
	 
	

}
