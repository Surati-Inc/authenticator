package com.security.authenticator.encryption;

import io.jsonwebtoken.Claims;

/**
 * Token represents all token norms
 * @author Olivier B. OURA (baudolivier.oura@gmail.com)
 *
 */
public interface Token {
	
	/**
	 * Claims stored in token
	 * @return Claims
	 */
	Claims claims();
	
	/**
	 * String version of token
	 * @return token in String
	 */
	String toString();
}
