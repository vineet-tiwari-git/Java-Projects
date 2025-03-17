package com.vineet.cartService.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vineet.cartService.model.LineItem;

public interface LineItemRepo extends JpaRepository<LineItem, Long> {

}
