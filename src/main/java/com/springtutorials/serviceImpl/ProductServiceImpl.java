package com.springtutorials.serviceImpl;


import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springtutorials.entity.Product;
import com.springtutorials.exception.ResourseNotFoundException;
import com.springtutorials.repository.ProductRepository;
import com.springtutorials.service.ProductService;


@Service
public class ProductServiceImpl implements ProductService{
	
	private final ProductRepository productRepository;
	

	public ProductServiceImpl(ProductRepository productRepository) {
		super();
		this.productRepository = productRepository;
	}

	@Override
	public Product createProduct(Product product) {
		Product createProduct = productRepository.save(product);
		return createProduct;
	}

	@Override
	public List<Product> createMutlipleProducts(List<Product> product) {
		List<Product> products = productRepository.saveAll(product);
		return products;
	}

	@Override
	public Product getProductById(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(()->new ResourseNotFoundException("Product "+id+" not found"));
		return product;
	}

	@Override
	public List<Product> getAllProducts() {
		List<Product> listAllProducts = productRepository.findAll();
		return listAllProducts;
	}

	@Override
	public Product updateProduct(Long id, Product product) {
		Product getProduct = productRepository.findById(id)
				.orElseThrow(()->new ResourseNotFoundException("Product "+id+" not found"));
		
		getProduct.setProductname(product.getProductname());
		getProduct.setDescription(product.getDescription());
		getProduct.setPrice(product.getPrice());
		getProduct.setStock(product.getStock());
		return productRepository.save(getProduct);
	}

	@Override
	public String deleteProductById(Long id) {
		Product getProduct = productRepository.findById(id)
				.orElseThrow(()->new ResourseNotFoundException("Product "+id+" not found"));
		productRepository.delete(getProduct);
		return "product "+ id + " deleted ";
	}

	@Override
	public List<Product> productsByStock(int stock) {
		List<Product> products = productRepository.findByStock(stock);
		return products;
	}

	/*@Override
	public List<Product> getProductsByPriceRange(double min, double max) {
	    List<Product> getProducts = productRepository.findAll().stream()
	    		.filter(p->p.getPrice()>=min && p.getPrice()<=max)
	    		.collect(Collectors.toList());
	    
		return getProducts;
	}*/
	
	@Override
	public List<Product> getProductsByPriceRange(double min, double max) {
		List<Product> getProducts = productRepository.findByPriceBetween(min, max);
		return getProducts;
	}

	@Override
	public Page<Product> getProducts(int stock, double minPrice
			                        , double maxPrice
			                        , String sortBy, Pageable pageable) {
		Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(sortBy));
		return productRepository.findByStockAndPriceBetween(stock, minPrice, maxPrice, sortedPageable);
	}


}
