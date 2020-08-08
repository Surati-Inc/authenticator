package com.security.authenticator.encryption;

import java.io.UnsupportedEncodingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.security.authenticator.exceptions.InvalidTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JwtToken is used for generating Jwt token
 * 
 * @author Olivier B. OURA (baudolivier.oura@gmail.com)
 *
 */
public final class JwtToken implements Token {

	/**
	 * Token string format
	 */
	private final String token;
	
	/**
	 * Secret key
	 */
	private final SecretKey secretKey;
	
	/**
	 * Ctor.
	 * @param token Token in string format
	 * @param secretKey Secret key
	 * @throws InvalidTokenException If token is invalid
	 */
	public JwtToken(final String token, final String secretKey) throws InvalidTokenException {
		this.token = token;
		this.secretKey = formatSecretKey(secretKey);
	}
	
	/**
	 * Ctor.
	 * @param claims Claims
	 * @param secretKey Secret key 
	 */
	public JwtToken(final Claims claims, final String secretKey) {
		this.secretKey = formatSecretKey(secretKey);
		this.token = generateToken(claims, this.secretKey);
	}
	
	/**
	 * Convert Secret key from string to SecretKey
	 * @param value String format
	 * @return SecretKey format
	 */
	private static SecretKey formatSecretKey(String value) {
		 		
		try {
			final byte[] bytes = value.getBytes("UTF-8");
			return new SecretKeySpec(bytes, 0, bytes.length, "AES");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	/**
	 * Generates Token in string format based on claims and secret key
	 * @param claims Claims
	 * @param secretKey Secret key
	 * @return Token string format
	 */
	private static String generateToken(final Claims claims, final SecretKey secretKey) {
		return Jwts.builder()
				   .setClaims(claims)
				   .signWith(SignatureAlgorithm.HS512, secretKey)
				   .compact();
	}
	
	@Override
	public Claims claims() {		
		return Jwts.parser()
				   .setSigningKey(secretKey)
				   .parseClaimsJws(token)			   
				   .getBody();	
	}
	
	@Override
	public String toString() {
		return token;
	}
}
