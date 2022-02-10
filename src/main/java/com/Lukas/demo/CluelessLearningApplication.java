package com.Lukas.demo;

import com.Lukas.demo.model.GithubUser;
import com.Lukas.demo.model.GoogleUser;
import com.Lukas.demo.service.CryptoOauth2UserService;
import com.Lukas.demo.service.CryptoOidcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@SpringBootApplication
@EnableWebSecurity
public class CluelessLearningApplication extends WebSecurityConfigurerAdapter {
	@Autowired
	private LoginSuccessHandler loginHandler;

	@Autowired
	private AuthorityMapper authMapper;

	public static void main(String[] args) {
		SpringApplication.run(CluelessLearningApplication.class, args);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/login/**").permitAll()
				// ROLE_USER seems to be given automatically, some sort of custom overrides seem to be required to give additional ones.
				.antMatchers("/api/**").hasAnyAuthority("ROLE_USER")
				.anyRequest().authenticated()
			.and()
				.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			.and()
				.logout()
			.and()
				.oauth2Login()
				.successHandler(loginHandler)
				.userInfoEndpoint()
					.customUserType(GoogleUser.class, "google")
					.customUserType(GithubUser.class, "github")
					.oidcUserService(new CryptoOidcUserService())
					.userService(new CryptoOauth2UserService())
					.userAuthoritiesMapper(authMapper);
	}
}
