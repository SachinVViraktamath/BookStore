package com.bridgelabz.bookstore.utility;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Component
public class JwtUtility {

	private static final String SCERET = "qwertyuiop";

	public String jwtToken(long a) throws UnsupportedEncodingException {
		String token = null;
		try {
			token = JWT.create().withClaim("userId", a).sign(Algorithm.HMAC512(SCERET));
		} catch (IllegalArgumentException | JWTCreationException e) {
			e.printStackTrace();
		}
		return token;
	}

	public long parse(String string)
			throws JWTVerificationException, IllegalArgumentException, UnsupportedEncodingException {
		Long userId = 0l;
		if (string != null) {
			userId = JWT.require(Algorithm.HMAC512(SCERET)).build().verify(string).getClaim("userId").asLong();
		}
		return userId;
	}
	
}
