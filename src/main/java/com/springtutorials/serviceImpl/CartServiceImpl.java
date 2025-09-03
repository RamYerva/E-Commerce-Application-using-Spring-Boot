package com.springtutorials.serviceImpl;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.springtutorials.entity.Cart;
import com.springtutorials.entity.CartItem;
import com.springtutorials.entity.Product;
import com.springtutorials.entity.User;
import com.springtutorials.repository.CartRepository;
import com.springtutorials.repository.ProductRepository;
import com.springtutorials.repository.UserRepository;
import com.springtutorials.service.CartService;



@Service
public class CartServiceImpl implements CartService {

	private final CartRepository cartRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;

	public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository,
			ProductRepository productRepository) {

		this.cartRepository = cartRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;

	}

	@Override
	public Cart saveProductToCart(Cart cart, Product product, int quantity) {
		

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.getUserByUsername(username);
		cart.setUser(user);
		cart = user.getCart();

		Product getProduct = productRepository.findById(product.getProductid()).get();
		
		CartItem item = new CartItem();
		item.setProduct(getProduct);
		item.setQuantity(quantity);
	    item.setCart(cart);
	    cart.getItems().add(item);
		return cartRepository.save(cart);
	}

	@Override
	public String deleteProduct(Long id) {
		cartRepository.deleteById(id);
		return "Product deleted Sucessfully";

	}

}
