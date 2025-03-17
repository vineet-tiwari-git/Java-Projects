package com.vineet.cartService.eureka.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vineet.cartService.exception.handling.CartServiceFeignErrorDecoder;
import com.vineet.cartService.model.Product;

@FeignClient(name = "Ecomm-ProductService", configuration = CartServiceFeignErrorDecoder.class)
public interface ProductService {

	@GetMapping(path = "products/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id);
}
