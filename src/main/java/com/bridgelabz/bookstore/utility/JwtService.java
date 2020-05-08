package com.bridgelabz.bookstore.utility;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtService {

	private static final String SCERET = "qwertyuiop";

	private final long TOKEN_VALIDITY=5*60*60;
	
	public String generateToken(Long id, Token expire) {
		if(expire.equals(Token.WITH_EXPIRE_TIME)) {
		return Jwts.builder().setSubject(String.valueOf(id))
				.setExpiration(new Date(System.currentTimeMillis() +TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512,SCERET).compact();
		}else {
			return Jwts.builder().setSubject(String.valueOf(id)).signWith(SignatureAlgorithm.ES512,SCERET).compact();
		}
	}
	
	public Long parse(String token)
	{
		System.out.println("Parsing");
		Claims claim=Jwts.parser().setSigningKey(SCERET).parseClaimsJws(token).getBody();
		Long id=Long.parseLong(claim.getSubject());
		return id;
	}
	
	public enum Token {
		WITH_EXPIRE_TIME,WITHOUT_EXPIRE_TIME
	}

}
