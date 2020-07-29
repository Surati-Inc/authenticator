package com.security.authenticator.encryption;

import java.sql.Date;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import com.security.authenticator.encryption.JwtToken;
import com.security.authenticator.encryption.Token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * Test case for {@link JwtToken}
 * @author Olivier B. OURA (baudoliver7@gmail.com)
 *
 */
public class JwtTokenTest {

	private static String SECRET_KEY = "My faith is in Jesus-Christ !";
	private static String SAMPLE_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNTk0MjUyODAwLCJpc3MiOiJBdXRoZW50aWNhdG9yIiwic3ViIjoidXNlciJ9.ycBFZfQ9grgWzNGltJ-Lhj-NqqD5NTiB9Chj6jnLY1_Y2avzN_k7NlmcbpNzOgpt95H04qqU3YGXKQB2vETCIA";
	private static Claims SAMPLE_CLAIMS = Jwts.claims()
											  .setId("1")
											  .setIssuedAt(Date.valueOf("2020-07-09"))
											  .setIssuer("Authenticator")
											  .setSubject("user");
	
	/**
	 * JwtToken can generate token
	 */
	@Test
	public void generateTokenTest() {
		
		final String expected = SAMPLE_TOKEN;
		final Token token = new JwtToken(SAMPLE_CLAIMS, SECRET_KEY);	
		
		MatcherAssert.assertThat(
			token.toString(), 
			Matchers.equalTo(expected)
		);
	}
	
	/**
	 * JwtToken can convert token
	 */
	@Test
	public void convertTokenTest() {
		
		final Token token = new JwtToken(SAMPLE_TOKEN, SECRET_KEY);
		final Claims claims = token.claims();
		
		MatcherAssert.assertThat(
			claims.getId(), 
			Matchers.equalTo("1")
		);
		
		MatcherAssert.assertThat(
			claims.getIssuer(), 
			Matchers.equalTo("Authenticator")
		);
		
		MatcherAssert.assertThat(
			claims.getIssuedAt(), 
			Matchers.equalTo(Date.valueOf("2020-07-09"))
		);
		
		MatcherAssert.assertThat(
			claims.getSubject(), 
			Matchers.equalTo("user")
		);
	}

}
