package com.springtutorials.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springtutorials.entity.OrderItem;

@Repository
public interface OrderitemRepository extends JpaRepository<OrderItem,Long >
{
	
	

}
