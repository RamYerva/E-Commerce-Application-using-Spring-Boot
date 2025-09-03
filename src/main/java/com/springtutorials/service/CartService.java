package com.springtutorials.service;

import com.springtutorials.entity.*;

public interface CartService {
	
	Cart saveProductToCart(Cart cart, Product product, int quantity);
	String deleteProduct(Long id);

}
