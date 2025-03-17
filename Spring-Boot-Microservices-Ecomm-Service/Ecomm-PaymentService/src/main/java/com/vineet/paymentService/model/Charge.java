package com.vineet.paymentService.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Charge {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Long orderId;

	private Date captureDate = new Date();

	private Double requestAmount = 0.00;

	private Double responseAmount = 0.00;

	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Date getCaptureDate() {
		return captureDate;
	}

	public void setCaptureDate(Date captureDate) {
		this.captureDate = captureDate;
	}

	public Double getRequestAmount() {
		return requestAmount;
	}

	public void setRequestAmount(Double requestAmount) {
		this.requestAmount = requestAmount;
	}

	public Double getResponseAmount() {
		return responseAmount;
	}

	public void setResponseAmount(Double responseAmount) {
		this.responseAmount = responseAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
