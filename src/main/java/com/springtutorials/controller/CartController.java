package com.springtutorials.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springtutorials.entity.Cart;
import com.springtutorials.service.CartService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cart")
@Tag(name="e-commerce user Cart",description = "Api for cart of user")
public class CartController {
	
	private final CartService cartService;
	
	
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}

    /*@PostMapping("/save")
	public ResponseEntity<Cart> saveProductsToCart(@RequestBody Cart cart){
		Cart getcart = cartService.saveProductToCart(cart);
		return ResponseEntity.ok(getcart);
	}*/
    
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "delete cart api")
    public ResponseEntity<String> deleteCartProduct(@PathVariable Long id){
    	return ResponseEntity.ok(cartService.deleteProduct(id));
    }

}
