package com.springtutorials.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springtutorials.dto.UserDTO;
import com.springtutorials.entity.User;
import com.springtutorials.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;



@RestController
@RequestMapping("/api/users")
@Tag(name="User e-commerce",description = "API for e-commerce")
public class UserController {
	
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/register")
	@Operation(summary = "user registration")
	public ResponseEntity<User> registerUser(@RequestBody UserDTO user){
		User registered = userService.registerUser(user);
		System.out.println("user details created in controller");
		return ResponseEntity.status(HttpStatus.CREATED).body(registered);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "get users by id")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		User user = userService.getUserById(id);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/email/{email}")
	@Operation(summary = "get users by email")
	public ResponseEntity<User> getUserByEmail(@PathVariable String email){
		User user = userService.getUserByEmail(email);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping
	@Operation(summary = "get all users")
	public ResponseEntity<java.util.List<User>> getAllUsers(){
		java.util.List<User> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}
	
	@PutMapping("/update/{id}")
	@Operation(summary = "update all users")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
		User existUser = userService.updateUser(id, user);
		return ResponseEntity.ok(existUser);
	}
	
	@DeleteMapping("/delete/{id}")
	@Operation(summary = "delete all users")
	public ResponseEntity<String> deleteUser(@PathVariable Long id){
		String msg = userService.deleteUser(id);
		return ResponseEntity.ok(msg);
	}
	
	@GetMapping("/me")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary = "get the user profile ")
	public ResponseEntity<User> getMyProfile(org.springframework.security.core.Authentication authentication){
		String username = authentication.getName();
		User user = userService.getUserByUsername(username);
		return ResponseEntity.ok(user);
	}
	
	@PutMapping("/me/update")
	@PreAuthorize("isAuthenticated()")
	@Operation(summary = "update the user profile")
	public ResponseEntity<User> updatemyprofile(Authentication authentication, @RequestBody User user){
		String username = authentication.getName();
		User updateduser = userService.updateUserByUsernsme(username, user);
		return ResponseEntity.ok(updateduser);
				
		
		
	}
	

}
