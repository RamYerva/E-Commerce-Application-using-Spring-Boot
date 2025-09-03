package com.springtutorials.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import com.springtutorials.entity.Product;
import com.springtutorials.service.ProductService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/products")
@Tag(name="e-commerce Product",description = "Api for product of user")
public class ProdutController {
	
	public final ProductService productService;

	public ProdutController(ProductService productService) {
		super();
		this.productService = productService;
	}
	
	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMIN')")
	 @Operation(summary = "create product")
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		Product createProduct = productService.createProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(createProduct);
	}
	
	@GetMapping
	 @Operation(summary = "get all products")
	public ResponseEntity<List<Product>> getAllProducts(){
		List<Product> products = productService.getAllProducts();
		return ResponseEntity.ok(products);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "get product by id")
	public ResponseEntity<Product> getProductById(@PathVariable Long id){
		Product product = productService.getProductById(id);
		return ResponseEntity.ok(product);
	}
	
	@PostMapping("/multiple")
	@Operation(summary = "create multiple products")
	public ResponseEntity<List<Product>> createMultipleProducts(@RequestBody List<Product> products){
		List<Product> createProducts = productService.createMutlipleProducts(products);
		return ResponseEntity.status(HttpStatus.CREATED).body(createProducts);
	}
	
	@PutMapping("/update")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "update the product")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product){
		Product updateProduct = productService.updateProduct(id, product);
		return ResponseEntity.ok(updateProduct);
	}
	
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	@Operation(summary = "delete the product")
	public ResponseEntity<?> deleteProductbyId(@PathVariable Long id){
		String  result = productService.deleteProductById(id);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/stock")
	@Operation(summary = "get the product stock")
	public ResponseEntity<List<Product>> productByStock(@RequestParam int stock){
		List<Product> product = productService.productsByStock(stock);
		return ResponseEntity.ok(product);
	}
	
	@GetMapping("/price-range")
	@Operation(summary = "get the product by the price range")
	public ResponseEntity<List<Product>> getProductsByPriceRange(
	        @RequestParam double min,
	        @RequestParam double max) {
		List<Product> products = productService.getProductsByPriceRange(min, max);
		return ResponseEntity.ok(products);
	}
	
	
	@GetMapping("/filter")
	@Operation(summary = "filtering the produts")
	public ResponseEntity<Page<Product>> getFilteredProducts(@RequestParam(defaultValue = "0") int page
			                                                 , @RequestParam(defaultValue = "10") int size
			                                                 , @RequestParam(defaultValue = "id") String sortBy
			                                                 , @RequestParam int stock
			                                                 , @RequestParam double min
			                                                 , @RequestParam double max){
		 Pageable pageable = PageRequest.of(page, size);
		 Page<Product> products = productService.getProducts(stock, min, max, sortBy, pageable);
		    return ResponseEntity.ok(products);
	}
}
