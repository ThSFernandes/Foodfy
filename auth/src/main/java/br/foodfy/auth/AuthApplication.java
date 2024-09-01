package br.foodfy.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "br.foodfy.auth.repositories")
@EntityScan(basePackages = "br.foodfy.auth.domain")

public class AuthApplication {

	public static void main(String[] args) {


		SpringApplication.run(AuthApplication.class, args);
	}

}