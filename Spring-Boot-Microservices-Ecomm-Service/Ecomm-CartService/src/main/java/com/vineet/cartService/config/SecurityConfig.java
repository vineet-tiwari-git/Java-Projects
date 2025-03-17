package com.vineet.cartService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.RequestInterceptor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;

@Configuration
public class SecurityConfig {

	@Bean
	public RequestInterceptor requestInterceptor() {

		return requestTemplate -> {
			requestTemplate.header("Authorization", String.format("Basic %s", "cGFzczpwYXNz"));
		};
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.securityMatcher("/**").authorizeHttpRequests((authz) -> authz.requestMatchers("/carts/**").authenticated())
				.httpBasic(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable);
		return http.build();
	}
}
