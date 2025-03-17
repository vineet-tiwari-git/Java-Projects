package com.vineet.productService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProductConfig {

	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.securityMatcher("/**").authorizeHttpRequests((authz) -> authz.requestMatchers("/products/**","/authors/**").authenticated()).httpBasic(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable);
		return http.build();
	}
	
}
