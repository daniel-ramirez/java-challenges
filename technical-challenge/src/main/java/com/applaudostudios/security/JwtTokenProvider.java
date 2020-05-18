package com.applaudostudios.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	private static String authHeader;

	private static String secret;

	private static Long expiration;

	private static String prefix;

	@Autowired
	private UserDetailsService userDetailsService;

	@Value("${spring.security.jwt.header}")
	public void setAuthHeader(String authHeaderValue) {
		authHeader = authHeaderValue;
	}

	@Value("${spring.security.jwt.secret}")
	public void setSecret(String secretValue) {
		secret = secretValue;
	}

	@Value("${spring.security.jwt.expiration}")
	public void setExpiration(Long expirationValue) {
		expiration = expirationValue;
	}

	@Value("${spring.security.jwt.prefix}")
	public void setPrefix(String prefixValue) {
		prefix = prefixValue;
	}

	/**
	 * Logic to add token into header
	 */
	public static void addToken(HttpServletResponse res, String username) {
		String jwtToken = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		res.addHeader(authHeader, prefix + " " + jwtToken); // construct bearer token
		res.addHeader("Access-Control-Expose-Headers", authHeader);
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		Authentication auth = null;
		String token = request.getHeader(authHeader);
		if (token != null) {
			// handle scenario when token has expired or corrupted!
			try {
				String username = Jwts.parser().setSigningKey(secret).parseClaimsJws(token.replace(prefix, ""))
						.getBody().getSubject();

				if (username != null) {
					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
					auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(),
							userDetails.getAuthorities());
				}
			} catch (Exception e) {
				// maybe do something with this exception
				auth = null;
			}
		}
		return auth;
	}

}
