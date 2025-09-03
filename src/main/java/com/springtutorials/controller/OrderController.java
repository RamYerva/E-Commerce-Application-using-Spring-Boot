package com.springtutorials.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springtutorials.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.springtutorials.entity.*;
import java.util.*;

@RestController
@RequestMapping("/api/orders")
@Tag(name="e-commerce user Orders",description = "Api for order of user")
public class OrderController {
	
	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		super();
		this.orderService = orderService;
	}
	
	@PostMapping("/create")
	 @Operation(summary = "create the order")
	public ResponseEntity<Order> createOrder(@RequestBody Order order) {
		Order neworder = orderService.createOrder(order);
		return ResponseEntity.status(HttpStatus.CREATED).body(neworder);
	}
	
	 @GetMapping
	 @Operation(summary = "get all orders")
	    public ResponseEntity<List<Order>> getAllOrders() {
	        return ResponseEntity.ok(orderService.getAllOrders());
	    }

	    @GetMapping("/{id}")
	    @Operation(summary = "get order by order id")
	    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
	        return ResponseEntity.ok(orderService.getOrderById(id));
	    }

	    @GetMapping("/user/{userId}")
	    @Operation(summary = "order by user id")
	    public ResponseEntity<List<Order>> getOrdersByUserId(@PathVariable Long userId) {
	        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
	    }

	    @GetMapping("/status/{status}")
	    @Operation(summary = "get Order status")
	    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable String status) {
	        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
	    }

	    @PutMapping("/update")
	    @Operation(summary = "update the order")
	    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
	        return ResponseEntity.ok(orderService.updateOrder(id, order));
	    }

	    @DeleteMapping("/delete/{id}")
	    @Operation(summary = "delete order api")
	    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
	        return ResponseEntity.ok(orderService.deleteOrder(id));
	    }
	

}
