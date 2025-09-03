package com.springtutorials.serviceImpl;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springtutorials.entity.User;
import com.springtutorials.repository.UserRepository;

import jakarta.transaction.Transactional;


@Service
public class CustomUserdetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	public CustomUserdetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}



	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		   User user = userRepository.getUserByUsername(username);
		   if (user == null) {
		        throw new UsernameNotFoundException("User not found with username: " + username);
		    }
		    return new CustomUserDetails(user);
	}

}
