package com.springtutorials.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.springtutorials.entity.Cart;
import com.springtutorials.entity.CartItem;
import com.springtutorials.entity.Order;
import com.springtutorials.entity.OrderItem;
import com.springtutorials.entity.Order_Status;
import com.springtutorials.entity.Product;
import com.springtutorials.entity.ShippingAddress;
import com.springtutorials.entity.User;
import com.springtutorials.repository.CartItemRepository;
import com.springtutorials.repository.OrderRepository;
import com.springtutorials.repository.ProductRepository;
import com.springtutorials.repository.ShippingAddressRepository;
import com.springtutorials.repository.UserRepository;
import com.springtutorials.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;
	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final CartItemRepository cartItemRepository;
	private final ShippingAddressRepository shippingAddressRepository;
	

	public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
			     ProductRepository productRepository,CartItemRepository cartItemRepository,
			                           ShippingAddressRepository shippingAddressRepository)
    {
		
		
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.cartItemRepository = cartItemRepository;
		this.shippingAddressRepository = shippingAddressRepository;
	}

	@Override
	public Order getOrderById(Long id) {
		Order order = orderRepository.findById(id).get();
		return order;
	}

	@Override
	public List<Order> getAllOrders() {
		List<Order> orders = orderRepository.findAll();
		return orders;
	}

	@Override
	public Order createOrder(Order order) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String user = auth.getName();
		User getUser = userRepository.getUserByUsername(user);
		order.setUser(getUser);

		double total_price = 0;
		Product product = null;
		for (OrderItem item : order.getItems()) {
			if (item.getProduct() == null || item.getProduct().getProductname() == null) {
				throw new IllegalArgumentException("OrderItem must contain a valid Product ID");
			}
			
			product = productRepository.findByProductname(item.getProduct().getProductname());
			double price = product.getPrice();
			double quantity = item.getQuantity();

			total_price = total_price + price * quantity;

			item.setPrice(product.getPrice());

			if (product.getStock() < item.getQuantity()) {
				throw new IllegalArgumentException("Not enough stock available for " + product.getProductname());
			}

			item.setProduct(product);
			item.setOrder(order);
            
			int getStock = product.getStock() - item.getQuantity();
			product.setStock(getStock);
			productRepository.save(product);
		}

		order.setTotalAmount(total_price);
		// order.calculatingTotalAmount();
		order.setOrderDate(LocalDateTime.now());
		order.setStatus(Order_Status.PLACED);
		ShippingAddress address = order.getShippingAddress();
		
		ShippingAddress addressCopy = shippingAddressRepository.findAddressById(address.getId());
		  
		if(!addressCopy.getId().equals(address.getId())) {
			
		}
		
			addressCopy.setCity(address.getCity());
			addressCopy.setState(address.getState());
			addressCopy.setStreet(address.getStreet());
			addressCopy.setZipcode(address.getZipcode());
			addressCopy.setCountry(address.getCountry());
			addressCopy.setUser(getUser);
			//addressCopy.setOrder(List.of(order));
			
	        order.setShippingAddress(addressCopy);
		
		
        order.setPaymentMode(order.getPaymentMode());
        //shippingAddressRepository.save(addressCopy);
        
       
		Order createOrder = orderRepository.save(order);
		return createOrder;
	}

	
	@Override
	public List<Order> getOrdersByUserId(Long userId) {
		List<Order> getOrderByStatus = orderRepository.findByUserId(userId);
		return getOrderByStatus;
	}

	@Override
	public String deleteOrder(Long id) {
		Order order = orderRepository.findById(id).get();
		orderRepository.delete(order);
		return "Order " + id + " sucesfully deleted";
	}

	@Override
	public List<Order> getOrdersByStatus(String status) {
		List<Order> getOrderByStatus = orderRepository.findByStatus(status);
		return getOrderByStatus;
	}

	@Override
	public Order updateOrder(Long id, Order order) {
		Order getOrder = orderRepository.findById(id).get();
		getOrder.setStatus(order.getStatus());
		return orderRepository.save(getOrder);
	}

	@Override
	public Order placeOrderFromCart(Order order) {

		double total_amount = 0;

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.getUserByUsername(auth);
		order.setUser(user);
		Cart cart = user.getCart();

		List<CartItem> cartItems = cart.getItems();
		List<OrderItem> orderItems = new ArrayList<>();
		for (CartItem items : cartItems) {
			Product product = items.getProduct();

			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(product);
			orderItem.setPrice(product.getPrice());
			orderItem.setOrder(order);
			orderItem.setQuantity(items.getQuantity());

			total_amount = total_amount + product.getPrice() * items.getQuantity();
			product.setStock(product.getStock() - items.getQuantity());
			productRepository.save(product);
			orderItems.add(orderItem);
		}

		order.setOrderDate(LocalDateTime.now());
		order.setItems(orderItems);
		order.setStatus(Order_Status.PLACED);
		order.setTotalAmount(total_amount);
 
		cartItemRepository.deleteAll(cartItems);

		return orderRepository.save(order);
	}

}
