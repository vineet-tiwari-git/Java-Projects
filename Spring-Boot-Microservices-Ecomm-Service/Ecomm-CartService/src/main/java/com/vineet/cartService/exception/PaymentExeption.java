package com.vineet.cartService.exception;

public class PaymentExeption extends RuntimeException {
	public PaymentExeption() {
	}

	public PaymentExeption(String message) {
		super(message);
	}

	public PaymentExeption(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		return "PaymentExeption: " + getMessage();
	}

}
