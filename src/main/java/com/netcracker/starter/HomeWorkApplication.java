package com.netcracker.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.netcracker")
public class HomeWorkApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeWorkApplication.class, args);
	}

}
