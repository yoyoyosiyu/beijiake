package com.beijiake.apps_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.beijiake.data.domain"})
@EnableJpaRepositories(basePackages = "com.beijiake.repository")
public class AppsServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppsServicesApplication.class, args);
	}

}
