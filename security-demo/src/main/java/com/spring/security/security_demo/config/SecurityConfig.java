package com.spring.security.security_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	 @Bean
	  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        return http.authorizeHttpRequests(request ->
	        request.anyRequest().authenticated())
	        .httpBasic(Customizer.withDefaults())
	        .build();
	    }
}

