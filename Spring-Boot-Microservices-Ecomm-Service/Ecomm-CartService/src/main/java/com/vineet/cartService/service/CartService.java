package com.vineet.cartService.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vineet.cartService.eureka.clients.PaymentService;
import com.vineet.cartService.eureka.clients.ProductService;
import com.vineet.cartService.model.Cart;
import com.vineet.cartService.model.Charge;
import com.vineet.cartService.model.Product;
import com.vineet.cartService.repo.CartRepo;
import com.vineet.cartService.exception.PaymentExeption;

@Service
public class CartService {

	@Autowired
	private CartRepo cartRepo;

	@Autowired
	private ProductService productService;

	@Autowired
	private PaymentService paymentService;

	public Cart createCart(Cart cart) {
		this.updateProductOnLineItem(cart);
		this.rollUpTotals(cart);
		Cart createdCart = this.cartRepo.save(cart);
		this.updateProductOnLineItem(createdCart);
		return createdCart;
	}

	public Optional<Cart> createOrder(Long id) throws PaymentExeption {
		Optional<Cart> cartOpt = this.cartRepo.findById(id);
		return cartOpt.map(c -> this.processCharge(c));
	}

	public Cart processCharge(Cart cart) throws PaymentExeption {
		this.updateProductOnLineItem(cart);
		Charge chargeRequest = this.createChargeRequest(cart);
		Charge chargeResponse = this.paymentService.createCharge(chargeRequest).getBody();
		if (chargeResponse.getStatus().equalsIgnoreCase("Success")) {
			cart.setState("Complete");
		} else {
			throw new PaymentExeption("Payment auth failure");
		}
		return this.cartRepo.save(cart);
	}

	public Optional<Cart> getCart(Long id) {
		return this.cartRepo.findById(id);
	}

	public void deleteCart(Long id) {
		this.cartRepo.deleteById(id);
	}

	public void rollUpTotals(Cart cart) {
		cart.getItems().forEach(li -> li.setTotal(li.getPricePerQty() * li.getQuantity()));
		cart.setTotal(cart.getItems().stream().mapToDouble(f -> f.getTotal()).sum());
	}

	public void updateProductOnLineItem(Cart cart) {
		cart.getItems().forEach(li -> {
			Product product = this.productService.getProductById(li.getProductId()).getBody();
			li.setProduct(product);
		});
	}

	public Charge createChargeRequest(Cart cart) {
		Charge charge = new Charge();
		charge.setOrderId(cart.getCartId());
		charge.setRequestAmount(cart.getTotal());
		return charge;
	}

}
