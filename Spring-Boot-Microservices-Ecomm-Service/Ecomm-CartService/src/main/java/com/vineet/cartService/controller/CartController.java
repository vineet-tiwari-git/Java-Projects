package com.vineet.cartService.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vineet.cartService.eureka.clients.ProductService;
import com.vineet.cartService.model.Cart;
import com.vineet.cartService.model.Product;
import com.vineet.cartService.service.CartService;

@RestController
@RequestMapping(path = "carts")
public class CartController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CartService cartService;

	@GetMapping("/{id}")
	public ResponseEntity<Cart> getCart(@PathVariable Long id) {
		return this.cartService.getCart(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public void deleteCart(@PathVariable Long id) {
		this.cartService.deleteCart(id);
	}

	@PostMapping
	public ResponseEntity<Cart> createCart(@RequestBody Cart cart) {
		return ResponseEntity.ok(this.cartService.createCart(cart));
	}

	@GetMapping("/test")
	public ResponseEntity<String> defaultUrl() {
		return ResponseEntity.ok("Hello from Cart Service !");
	}

	@PostMapping(path = "/{id}/createOrder")
	public ResponseEntity<Cart> createOrder(@PathVariable Long id) {
		
		Optional<Cart> orderOpt = this.cartService.createOrder(id);
		if(orderOpt.isPresent()) {
			Cart order = orderOpt.get();
			if(order.getState().equalsIgnoreCase("Complete")){
				return ResponseEntity.ok(order);
			} else {
				return new ResponseEntity(order,HttpStatusCode.valueOf(409));
			}
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
