package com.vineet.cartService.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;

@Entity
public class LineItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long lineItemId;
	
	private Long productId;
	
	private Long quantity;
	
	private Double pricePerQty;
	
	private Double total;
	
	@Transient
	private Product product;
	
	@ManyToOne
	@JoinColumn(name = "cartId")
	@JsonBackReference
	private Cart cart;

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Long getLineItemId() {
		return lineItemId;
	}

	public void setLineItemId(Long lineItemId) {
		this.lineItemId = lineItemId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Double getPricePerQty() {
		return pricePerQty;
	}

	public void setPricePerQty(Double pricePerQty) {
		this.pricePerQty = pricePerQty;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
