package com.vineet.productService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vineet.productService.model.Product;
import com.vineet.productService.service.ProductService;

@RestController
@RequestMapping(path = "/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping(path = "/{id}")
	private ResponseEntity<Product> getProductById(@PathVariable Long id) {
		return this.productService.getProductById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	private ResponseEntity<Product> createProduct(@RequestBody Product product) {
		return ResponseEntity.ok(this.productService.createProduct(product));
	}

	@GetMapping
	private ResponseEntity<List<Product>> getProducts() {
		return ResponseEntity.ok(this.productService.getProducts());
	}

	@DeleteMapping(path = "/{id}")
	private void deleteProductById(@PathVariable Long id) {
		this.productService.deleteProductById(id);
	}

}
