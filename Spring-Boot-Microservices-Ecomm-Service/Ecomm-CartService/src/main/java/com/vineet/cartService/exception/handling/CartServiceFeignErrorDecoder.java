package com.vineet.cartService.exception.handling;

import com.vineet.cartService.exception.BadRequestException;
import com.vineet.cartService.exception.NotFoundException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CartServiceFeignErrorDecoder implements ErrorDecoder {

	private final ErrorDecoder errorDecoder = new Default();

	@Override
	public Exception decode(String methodKey, Response response) {
		
		switch (response.status()) {
		case 400:
			return new BadRequestException(" My bad Exception");
		case 404:
			return new NotFoundException("My own Not found !!!");
		default:
			return new Exception("My Generic Error ");
		}
	}
}