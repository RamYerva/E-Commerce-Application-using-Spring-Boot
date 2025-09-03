package com.springtutorials.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springtutorials.entity.User;
import com.springtutorials.security.JwtService;
import com.springtutorials.serviceImpl.CustomUserdetailsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication-for Roles", description="Authentication api for the roles")
public class AuthController {

	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
    private final CustomUserdetailsService customUserdetailsService;

	public AuthController(JwtService jwtService, AuthenticationManager authenticationManager,
			CustomUserdetailsService customUserdetailsService) {

		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
		this.customUserdetailsService = customUserdetailsService;
	}

	@PostMapping("/login")
	@Operation(summary = "login api")
	public ResponseEntity<?> login(@RequestBody User request) {

		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

			 UserDetails userDetails = customUserdetailsService.loadUserByUsername(request.getUsername());
			 String token = jwtService.jwtTokenGenerate(userDetails);

			return ResponseEntity.ok("Bearer " + token);
		} catch (Exception e) {
			return ResponseEntity.status(401).body("Invalid credentials");
		}
	}

}
