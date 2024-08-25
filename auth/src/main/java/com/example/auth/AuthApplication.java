package com.example.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.auth.repositories")
@EntityScan(basePackages = "com.example.auth.domain")

public class AuthApplication {

	public static void main(String[] args) {


		SpringApplication.run(AuthApplication.class, args);
	}

}
