package com.onlineretail.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfigUtil extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureInMemoryUsers(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("yuvi").password("{noop}yuvi").roles("ADMIN", "USER");
	}
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable().and().authorizeRequests()
				.antMatchers("/v1/**", "/v2/**", "/swagger-ui/**", "/api-docs/**", "/h2-console/**").permitAll()
				.antMatchers("/products/**", "/bills/**").authenticated().and().httpBasic().realmName("mystore").and()
				.csrf().disable();
	}

}
