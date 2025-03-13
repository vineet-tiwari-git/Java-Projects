package com.vineet.bookService;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class MyConfiguration {

	@Bean
	UserDetailsService userDetailService() {

		System.out.println(">>>>>>>>>>>>>>>> userDetailService latest >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username("ussr")
				.password("ussr")
				.roles("USER")
				.build();

		System.out.println("adminUser.getPassword():" + user.getPassword());
		System.out.println("adminUser.getUsername():" + user.getUsername());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(">>>>>>>>>>>>>>>>>>>userDetailService latest >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		return new InMemoryUserDetailsManager(user);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.securityMatcher("/**").authorizeHttpRequests((authz) -> authz.requestMatchers("/books/**","/authors/**","/actuator/**").authenticated()).httpBasic(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable);
		return http.build();
	}
}
