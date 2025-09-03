package com.springtutorials.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.springtutorials.dto.UserDTO;
import com.springtutorials.entity.Role;
import com.springtutorials.entity.User;
import com.springtutorials.exception.ResourseNotFoundException;
import com.springtutorials.repository.UserRepository;
import com.springtutorials.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
	public UserServiceImpl(UserRepository userRepository,PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public User registerUser(UserDTO userDTO) {
		Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
		System.out.println("user details service");
		if(existingUser.isPresent()) {
			throw new RuntimeException("User already exists");
		}
		User user = new User();
		user.setEmail(userDTO.getEmail());
		user.setUsername(userDTO.getUsername());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setPhone(userDTO.getPhone());
		user.setRole(Role.CUSTOMER);
		userRepository.save(user);
		return user;
	}
	

	@Override
	public User getUserById(long id) {
		User user =  userRepository.findById(id).get();
		return user;
	}


	@Override
	public List<User> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users;
	}

	@Override
	public User getUserByEmail(String email) {
	    return userRepository.findByEmail(email)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
	}

	@Override
	public User updateUser(Long id, User user) {
		User existingUser = userRepository.findById(id).get();
		
		existingUser.setUsername(user.getUsername());
		existingUser.setEmail(user.getEmail());
		existingUser.setPhone(user.getPhone());
		userRepository.save(existingUser);
		return existingUser;
	}

	@Override
	public String deleteUser(Long id) {
		User existingUser = userRepository.findById(id).get();
		userRepository.delete(existingUser);
		
		return "user " + id + " deleted succesfully";
	}

	@Override
	public User getUserByUsername(String username) {
		return userRepository.getUserByUsername(username);
	}

	@Override
	public User updateUserByUsernsme(String username, User user) {
		
			User existingUser = userRepository.getUserByUsername(username);
			if (existingUser == null) {
		        throw new ResourseNotFoundException("User not found with username: " + username);
		    }
				existingUser.setUsername(user.getUsername());
				existingUser.setEmail(user.getEmail());
				existingUser.setPhone(user.getPhone());
				return userRepository.save(existingUser);
	}


	
	
	
	
	
	
	

}
