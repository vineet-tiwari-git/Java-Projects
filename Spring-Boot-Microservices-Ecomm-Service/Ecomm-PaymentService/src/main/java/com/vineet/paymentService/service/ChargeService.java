package com.vineet.paymentService.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vineet.paymentService.model.Charge;
import com.vineet.paymentService.repo.ChargeRepo;

@Service
public class ChargeService {

	@Autowired
	private ChargeRepo chargesRepo;

	public Charge createCharge(Charge charge) {
		if(charge.getRequestAmount() <= 50) {
			charge.setResponseAmount(charge.getRequestAmount());
			charge.setStatus("Sucess");	
		} else {
			charge.setResponseAmount(0.00);
			charge.setStatus("Failed");
		}
		return this.chargesRepo.save(charge);
	}
	
	public Optional<Charge> getChargeById(Long id) {
		return this.chargesRepo.findById(id);
	}

}
