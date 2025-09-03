package com.springtutorials.serviceImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.springtutorials.entity.Role;
import com.springtutorials.entity.User;

public class CustomUserDetails implements UserDetails{
	
	private final Long id;
	private final String email;
	private final String username;
	private final String password;
	private final String phone;
	private final List<SimpleGrantedAuthority> role;
	
	
	public CustomUserDetails(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.username= user.getUsername();
		this.password= user.getPassword();
		this.phone= user.getPhone();
		
		this.role = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
	}
	
	

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role;
    }
	
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return username;
	}
	
	 @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }

	    public Long getId() {
	        return id;
	    }

	    public String getPhone() {
	        return phone;
	    }

	    public List<SimpleGrantedAuthority> getRole() {
	        return role;
	    }
	}
	
	
	
	
	

