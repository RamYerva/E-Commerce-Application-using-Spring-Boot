package com.springtutorials.orderstest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

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
import com.springtutorials.repository.OrderRepository;
import com.springtutorials.repository.OrderitemRepository;
import com.springtutorials.repository.ProductRepository;
import com.springtutorials.repository.UserRepository;
import com.springtutorials.serviceImpl.OrderServiceImpl;


@ExtendWith(MockitoExtension.class)
public class CreateOrderTest {
	
	@InjectMocks
	private OrderServiceImpl orderServiceImpl;
	@Mock
	private ProductRepository productRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private OrderitemRepository orderitemRepository;
	@Test
	public void createOrderTest() {
		
		Authentication auth = Mockito.mock(Authentication.class);
		SecurityContext context = Mockito.mock(SecurityContext.class);
		Mockito.when(context.getAuthentication()).thenReturn(auth);
		Mockito.when(auth.getName()).thenReturn("ram");
		SecurityContextHolder.setContext(context);
		
		User user = new User();
		user.setUsername("ram");
		
		Product product = new Product();
		product.setProductname("laptop");
		product.setDescription("an Apple Product Laptop");
		product.setPrice(100000);
		product.setStock(22);
		
		OrderItem item = new OrderItem();
		item.setProduct(product);
		item.setQuantity(2);
		
		Order order = new Order();
		order.setItems(List.of(item));
		order.setUser(user);
		
		
		Mockito.when(userRepository.getUserByUsername("ram")).thenReturn(user);
		Mockito.when(productRepository.findByProductname("laptop")).thenReturn(product);
		//Mockito.when(orderRepository.g).thenReturn(null)
		Mockito.when(orderRepository.save(any(Order.class))).thenAnswer(i->i.getArgument(0));
		
		Order created = orderServiceImpl.createOrder(order);

		
		assertNotNull(created);
		assertNotNull(product);
		assertNotNull(user);
		assertEquals(user,created.getUser());
		assertEquals("laptop", product.getProductname());
		assertEquals(200000, created.getTotalAmount());
		assertEquals(20, product.getStock());
		
		
		
		
		
	}

}
