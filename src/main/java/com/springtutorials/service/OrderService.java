package com.springtutorials.service;

import com.springtutorials.entity.Order;
import java.util.*;


public interface OrderService {
	
	Order getOrderById(Long id);
	java.util.List<Order> getAllOrders();
	 Order createOrder(Order order);
	 List<Order> getOrdersByUserId(Long userId);
	 String deleteOrder(Long id);
	 List<Order> getOrdersByStatus(String status);
	 Order updateOrder(Long id,Order order);
	 Order placeOrderFromCart(Order order);


	 
	
	
	

}
