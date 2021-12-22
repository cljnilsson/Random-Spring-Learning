package com.Lukas.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
public class CluelessLearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(CluelessLearningApplication.class, args);
	}
}
