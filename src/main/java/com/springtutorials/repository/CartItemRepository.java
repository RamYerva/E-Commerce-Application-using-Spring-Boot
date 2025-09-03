package com.springtutorials.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springtutorials.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

}
