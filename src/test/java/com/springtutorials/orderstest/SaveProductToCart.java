package com.springtutorials.orderstest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.springtutorials.entity.*;
import com.springtutorials.repository.CartRepository;
import com.springtutorials.repository.ProductRepository;
import com.springtutorials.repository.UserRepository;
import com.springtutorials.serviceImpl.CartServiceImpl;


@ExtendWith(MockitoExtension.class)
public class SaveProductToCart {
	
	@InjectMocks
	private CartServiceImpl cartServiceImpl;
	@Mock
	private CartRepository cartRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private ProductRepository productRepository;
	
	@Test
	public void createCartFromProduct() {
		
		Authentication auth = Mockito.mock(Authentication.class);
		SecurityContext context = Mockito.mock(SecurityContext.class);
		Mockito.when(context.getAuthentication()).thenReturn(auth);
		Mockito.when(auth.getName()).thenReturn("ram");
		SecurityContextHolder.setContext(context);
		
		User user = new User();
		user.setUsername("ram");
		
		Product product = new Product();
		product.setProductname("watch");
		product.setDescription("a watch");
		product.setPrice(1000);
		product.setStock(78);
		
		List<CartItem> items = new ArrayList<>();
		for(CartItem item : items) {
			item.setProduct(product);
		}
		
		Cart cart = new Cart();
		cart.setItems(items);
		cart.setUser(user);
		
		Mockito.when(userRepository.getUserByUsername("ram")).thenReturn(user);
		Mockito.when(cartRepository.save(any(Cart.class))).thenAnswer(i->i.getArgument(0));
		
		 Cart created = cartServiceImpl.saveProductToCart(cart);
		
		assertEquals("watch",created.getItems());
		assertEquals("ram", created.getUser());
		
		
		
	}

}
