package com.dotple;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DotpleApplication {

	public static void main(String[] args) {
		SpringApplication.run(DotpleApplication.class, args);
	}

}
