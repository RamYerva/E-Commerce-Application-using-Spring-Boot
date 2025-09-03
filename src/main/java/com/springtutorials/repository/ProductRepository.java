package com.springtutorials.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springtutorials.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	
	List<Product> findByStock(int stock);
	
	Product findByProductname(String productname);
	
	List<Product> findByPriceBetween(double min, double max);

	Page<Product> findByStockAndPriceBetween(int stock, double minPrice, double maxPrice, Pageable sortedPageable);

}
