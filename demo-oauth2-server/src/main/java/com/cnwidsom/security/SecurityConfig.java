package com.cnwidsom.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("123456").roles("USER", "CLIENT");
		auth.inMemoryAuthentication().withUser("admin").password("123456").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("dba").password("123456").roles("CLIENT");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/oauth/**").access("hasRole('USER') or hasRole('CLIENT')")
				.antMatchers("/security/authorize").access("hasRole('USER') or hasRole('CLIENT')").antMatchers("/**")
				.permitAll().and().formLogin().loginPage("/security/login").failureUrl("/security/login?error")
				.loginProcessingUrl("/login").usernameParameter("username").passwordParameter("password").permitAll()
				.and().logout().logoutSuccessUrl("/security/login?logout").permitAll().and().csrf();

	}
}
