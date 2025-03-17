package com.vineet.paymentService.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vineet.paymentService.model.Charge;
import com.vineet.paymentService.service.ChargeService;

@RestController
@RequestMapping(path = "charge")
public class ChargeController {

	@Autowired
	private ChargeService chargeService;

	@PostMapping(path = "create")
	private ResponseEntity<Charge> createCharge(@RequestBody Charge charge) {
		return ResponseEntity.ok(this.chargeService.createCharge(charge));
	}

	@GetMapping(path = "/{id}")
	private ResponseEntity<Charge> getCharge(@PathVariable Long id) {
		return this.chargeService.getChargeById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
}
