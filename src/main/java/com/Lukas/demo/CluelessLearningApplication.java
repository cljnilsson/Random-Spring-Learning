package com.Lukas.demo;

import com.Lukas.demo.model.GithubUser;
import com.Lukas.demo.model.GoogleUser;
import com.Lukas.demo.service.CustomOauth2UserService;
import com.Lukas.demo.service.CustomOidcUserService;
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
				.antMatchers("/").hasRole("WHITELIST") // It's a bit backwards but whitelist works better with spring
				.antMatchers("/api/**").hasAnyAuthority("ROLE_SUPERUSER")
				.antMatchers("/login/**", "/error").permitAll()
				.anyRequest().authenticated()
			.and()
				.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			.and()
				.logout()
			.and()
				.oauth2Login()
				.successHandler(loginHandler)
				.userInfoEndpoint()
					// While it is named GoogleUser and GithubUser§, in practice it's basically OauthUser and OidcUser until updated
					.customUserType(GoogleUser.class, "google")
					.customUserType(GithubUser.class, "github")
					.oidcUserService(new CustomOidcUserService())
					.userService(new CustomOauth2UserService())
					.userAuthoritiesMapper(authMapper);
	}
}
