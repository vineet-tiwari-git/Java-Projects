package com.vineet.cartService.eureka.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vineet.cartService.model.Charge;

@FeignClient(name = "Ecomm-PaymentService")
public interface PaymentService {

	@GetMapping(path = "charge/create")
	public ResponseEntity<Charge> createCharge(@RequestBody Charge charge);
}
