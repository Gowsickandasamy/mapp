package com.team2.urbanvoice.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.team2.urbanvoice.service.CustomSuccessHandler;
import com.team2.urbanvoice.serviceimpl.CustomUserDetailsImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Autowired
	CustomSuccessHandler customSuccessHandler;
	@Autowired
	CustomUserDetailsImpl customUserDetailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		logger.info("Configuring Security for the Application !");
		
		http.authorizeHttpRequests(request -> request
				.requestMatchers("/urbanvoice/admin/track-complaints", "/urbanvoice/admin/**").hasAuthority("ADMIN")
				.requestMatchers("/urbanvoice/user/track-complaints", "/urbanvoice/user/**").hasAuthority("USER")
				.requestMatchers("/urbanvoice/officer/track-complaints", "/urbanvoice/officer/**").hasAuthority("OFFICER")
				.requestMatchers("/urbanvoice/registration", "/css/**", "/urbanvoice", "/urbanvoice/generateEmailOtp",
						"/urbanvoice/validateOtp")
				.permitAll().anyRequest().authenticated())
		
				.formLogin(form -> form.loginPage("/urbanvoice/login").loginProcessingUrl("/urbanvoice/login")
						.successHandler(customSuccessHandler).permitAll())

				.logout(form -> form.invalidateHttpSession(true).clearAuthentication(true)
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/urbanvoice/login?logout").permitAll())
				
				.sessionManagement(session -> session.invalidSessionUrl("/login?timeout"));

		logger.info("Application Security Configured Successfully !");
		return http.build();
	}
	
	@Bean
	private static PasswordEncoder passwordEncoder() {
		logger.info("Encrypting password using BCrypt Encoder !");
		
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private void configure(AuthenticationManagerBuilder auth) throws Exception {
		logger.info("Configuring Authentication Manager !");
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
		logger.info("Authentication Manager Configured Successfully !");
	}
}