package com.interview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ConvoyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConvoyServiceApplication.class, args);
	}
}
