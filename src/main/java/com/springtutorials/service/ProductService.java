package com.springtutorials.service;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springtutorials.entity.*;


public interface ProductService {

	Product createProduct(Product product);
	List<Product> createMutlipleProducts(List<Product> product);
	Product getProductById(Long id);
	List<Product> getAllProducts();
	Product updateProduct(Long id, Product product);
	String deleteProductById(Long id);
	List<Product> productsByStock(int stock);
	List<Product> getProductsByPriceRange(double min, double max);
	
	Page<Product> getProducts(int stock, double minPrice, double maxPrice, String sortBy, Pageable pageable);


}
