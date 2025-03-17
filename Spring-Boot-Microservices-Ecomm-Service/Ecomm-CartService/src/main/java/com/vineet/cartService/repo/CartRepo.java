package com.vineet.cartService.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vineet.cartService.model.Cart;

public interface CartRepo extends JpaRepository<Cart, Long> {

}
