package com.vineet.productService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vineet.productService.model.Product;
import com.vineet.productService.repo.ProductRepo;

@Service
public class ProductService {

	@Autowired
	private ProductRepo productRepo;

	public Optional<Product> getProductById(Long id) {
		return this.productRepo.findById(id);
	}

	public List<Product> getProducts() {
		return this.productRepo.findAll();
	}

	public Product createProduct(Product product) {
		return this.productRepo.save(product);
	}

	public void deleteProductById(Long id) {
		this.productRepo.deleteById(id);
	}

}