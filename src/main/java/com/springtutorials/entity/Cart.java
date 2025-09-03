package com.springtutorials.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartid;
	@OneToOne
	private User user;
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
	private List<CartItem> items;
	
	
	public Long getId() {
		return cartid;
	}
	public void setId(Long cartid) {
		this.cartid = cartid;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<CartItem> getItems() {
		return items;
	}
	public void setItems(List<CartItem> items) {
		this.items = items;
	}
	

}
