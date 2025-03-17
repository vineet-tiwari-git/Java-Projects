package com.vineet.paymentService.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vineet.paymentService.model.Charge;

public interface ChargeRepo extends JpaRepository<Charge, Long> {

}
