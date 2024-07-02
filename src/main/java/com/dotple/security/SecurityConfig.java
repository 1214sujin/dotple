package com.dotple.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain SecureFilterChain(HttpSecurity http) throws Exception {

		http
				.securityMatcher("/check", "/category/**", "/task/**", "/todo/**", "/user/**", "/error")
				.authorizeHttpRequests((auth) -> auth
						.anyRequest().permitAll())
				.securityContext(AbstractHttpConfigurer::disable)
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(AbstractHttpConfigurer::disable);

		return http.build();
	}
}
