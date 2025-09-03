package com.springtutorials.security;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class JwtService {
	
	 private static final Logger log = LoggerFactory.getLogger(JwtService.class);
	
	@Value("${jwt.secret}")
    private String SECRET_KEY;
	//private SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	
	private SecretKey getSignInKey() {
	    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}
	
	public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}
	
	public String jwtTokenGenerate(UserDetails userDetails) {
		
		Map<String,Object> claims = new HashMap<>();
		claims.put("roles", userDetails.getAuthorities()
				.stream().map(t -> t.getAuthority()).toList());
		
		//log.info("Generating JWT for user: {}", userDetails.getUsername());
		
		return Jwts.builder()
				.setClaims(claims) 
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public Claims extractClaims(String token) {
		//System.out.println(Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody());
		return Jwts.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public boolean isTokenExpired(String token) {
		return extractClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
	}
	
	public boolean isTokenValid(String username, String token, UserDetails userDetails) {
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}
	
	public Date extractExpiration(String token) {
		return (Date) extractClaims(token).getExpiration();
	}
	

	
}
