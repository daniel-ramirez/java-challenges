package com.applaudostudios.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	public static final String ROLE_ADMIN = "ROLE_ADMIN";

	public static final String ROLE_USER = "ROLE_USER";

	@Autowired
	private UserDetailServiceImpl userDetailsService;

	@Autowired
	private JwtTokenFilter jwtTokenFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and()
				// Filter for the api/login requests
				.addFilterBefore(new LoginFilter("/api/login", authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)
				// Filter for other requests to check JWT in header
				.addFilterAfter(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests().antMatchers(HttpMethod.POST, "/api/login").permitAll()
				.antMatchers("/api/explorer/**").permitAll()
				.antMatchers("/h2-console/**/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/movies", "/api/movies/*").permitAll()
				.antMatchers(HttpMethod.POST, "/api/movies").hasAuthority(ROLE_ADMIN)
				.antMatchers(HttpMethod.PUT, "/api/movies/**").hasAuthority(ROLE_ADMIN)
				.antMatchers(HttpMethod.PATCH, "/api/movies/**").hasAnyAuthority(ROLE_ADMIN, ROLE_USER)
				.antMatchers(HttpMethod.DELETE, "/api/movies/**").hasAuthority(ROLE_ADMIN)
				.antMatchers(HttpMethod.GET, "api/movies/search/custom-filter").hasAuthority(ROLE_ADMIN)
				.antMatchers("/api/purchase-orders/**/**", "/api/purchase-order-detail/**/**").authenticated()
				.anyRequest().hasAuthority(ROLE_ADMIN).and()
				//TODO NEED FIX - Logout is not working properly, because is not invalidating token
				.logout(logout -> logout.deleteCookies("remove")
						.invalidateHttpSession(true)
						.logoutUrl("/api/logout")
						.logoutSuccessUrl("/api/movies")
				);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("*"));
		config.setAllowedMethods(Arrays.asList("*"));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setAllowCredentials(true);
		config.applyPermitDefaultValues();

		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

}