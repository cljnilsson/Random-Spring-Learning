package com.Lukas.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

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
