package com.springtutorials.admin;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.springtutorials.entity.*;
import com.springtutorials.exception.AdminCreationEcxception;
import com.springtutorials.repository.UserRepository;

@Configuration
public class Datainitializer {
	@Bean
	CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		
		String adminEmail = "admin@e-commerce.com";
		return args -> {
		try {
		Optional<User> adminOptional = userRepository.findByEmail(adminEmail);
		
		if(adminOptional.isEmpty()) {
			User admin = new User();
			admin.setUsername("admin");
			admin.setEmail(adminEmail);
			admin.setPassword(passwordEncoder.encode("admin123"));
			admin.setPhone("9876543210");
			admin.setRole(Role.ADMIN);
			User saved = userRepository.save(admin);
			
			if(saved.getId()==null) {
				throw new AdminCreationEcxception("Admin creation failed");
			}
		}
	}
		catch (Exception e) {
			throw new AdminCreationEcxception("Admin creation error " + e.getMessage());
		}
		};
	}
}
