package com.security.authenticator.exceptions;

/**
 * Invalid token Exception
 * @author Olivier B. OURA (baudoliver7@gmail.com)
 *
 */
public final class InvalidTokenException extends RuntimeException {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Ctor.
	 */
	public InvalidTokenException() {
		super("Authentication required !");
	}

}
