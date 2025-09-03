package com.springtutorials.serviceImpl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.springtutorials.entity.Order;
import com.springtutorials.entity.ShippingAddress;
import com.springtutorials.entity.User;
import com.springtutorials.repository.OrderRepository;
import com.springtutorials.repository.ShippingAddressRepository;
import com.springtutorials.repository.UserRepository;
import com.springtutorials.service.ShippingAddressService;


@Service
public class ShippingAddressImpl implements ShippingAddressService{
	
	private final ShippingAddressRepository shippingAddressRepository;
	private final UserRepository userRepository;
	private final OrderRepository orderRepository;

	public ShippingAddressImpl(ShippingAddressRepository shippingAddressRepository,
			                                    UserRepository userRepository,
			                                    OrderRepository orderRepository) {
		super();
		this.shippingAddressRepository = shippingAddressRepository;
		this.userRepository = userRepository;
		this.orderRepository = orderRepository;
	}

	@Override
	public ShippingAddress createAddress(ShippingAddress address) {
		/*String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		User username = userRepository.getUserByUsername(auth);
		
		Order order = new Order();
		address.setOrder(order);
		address.setUser(username);
		
		orderRepository.save(order);
		
		return shippingAddressRepository.save(address);*/
		return null;
		
		
		
	}

}
