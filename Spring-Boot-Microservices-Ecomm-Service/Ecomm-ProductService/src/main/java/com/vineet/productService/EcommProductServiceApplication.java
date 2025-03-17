package com.vineet.productService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EcommProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommProductServiceApplication.class, args);
	}

}
