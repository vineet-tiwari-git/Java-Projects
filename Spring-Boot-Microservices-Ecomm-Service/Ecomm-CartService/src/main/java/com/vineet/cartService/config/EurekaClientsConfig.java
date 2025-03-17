package com.vineet.cartService.config;

import org.springframework.context.annotation.Bean;

import com.vineet.cartService.exception.handling.CartServiceFeignErrorDecoder;

import feign.codec.ErrorDecoder;

public class EurekaClientsConfig {

	@Bean
	public ErrorDecoder errorDecoder() {
		return new CartServiceFeignErrorDecoder();
	}
}
