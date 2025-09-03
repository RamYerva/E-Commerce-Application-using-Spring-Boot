package com.springtutorials.service;

import java.util.List;

import com.springtutorials.dto.UserDTO;
import com.springtutorials.entity.User;

public interface UserService{
    
	User registerUser(UserDTO userDTO);
	
	List<User> getAllUsers();
	
    User getUserById(long id);

	User getUserByEmail(String email);
	
	User updateUser(Long id,User user);
	
	String deleteUser(Long id);
	
	User getUserByUsername(String username);
	
	User updateUserByUsernsme(String username, User user);


}
