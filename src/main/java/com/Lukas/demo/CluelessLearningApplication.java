package com.Lukas.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class CluelessLearningApplication extends WebSecurityConfigurerAdapter{
	public static void main(String[] args) {
		SpringApplication.run(CluelessLearningApplication.class, args);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/login").permitAll() // Allow this for all
				.anyRequest().authenticated()
			.and()
				.logout()
			.and()
				.oauth2Login();
	}
}
