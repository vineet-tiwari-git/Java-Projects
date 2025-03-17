package com.vineet.cartService.exception.handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vineet.cartService.exception.BadRequestException;
import com.vineet.cartService.exception.NotFoundException;
import com.vineet.cartService.exception.PaymentExeption;

@RestControllerAdvice
public class CartServiceExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<String> handleBadRequestException(BadRequestException ex){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}
	
	
	@ExceptionHandler(PaymentExeption.class)
	public ResponseEntity<String> handlePaymentExeption(PaymentExeption ex){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
}
