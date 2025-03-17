package com.vineet.productService.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vineet.productService.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
	
	

}
