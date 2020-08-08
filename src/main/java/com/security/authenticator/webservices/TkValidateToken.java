package com.security.authenticator.webservices;

import java.net.HttpURLConnection;

import javax.json.Json;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;
import org.takes.rs.RsJson;
import org.takes.rs.RsWithStatus;

import com.minlessika.db.Database;
import com.security.authenticator.encryption.JwtToken;
import com.security.authenticator.encryption.Token;
import com.security.authenticator.users.DbUsers;
import com.security.authenticator.users.Users;
import com.security.authenticator.web.Main;

import io.jsonwebtoken.Claims;

/**
 * Take that validates a token
 * 
 * <p>The class is immutable and thread-safe.</p>
 * 
 * @author Marcel Brou
 *
 */

public class TkValidateToken implements Take {
	
private final Database source;
	
	public TkValidateToken(final Database source) {
		this.source = source;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		
		final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
		
		final String token = form.single("token");
		
		try {
			final Token jwtToken = new JwtToken(token, Main.PASS_PHRASE);
			final Claims claims = jwtToken.claims();
			final String login = claims.getSubject();
			final String issuer = claims.getIssuer();
			
			final Users users = new DbUsers(source);
			
			if(users.has(login) && issuer.equals("Authenticator")) {
				return new RsWithStatus(
						new RsJson(
							Json.createObjectBuilder()
							.add("login", login)
							.build()
						),
						HttpURLConnection.HTTP_OK
				);
			} else {
				throw new IllegalArgumentException("Token is invalid !");
			}			
		} catch (IllegalArgumentException e) {
			// if an error
			return new RsWithStatus(
				new RsJson(
					Json.createObjectBuilder()
						.add("message", e.getLocalizedMessage())
						.build()
					),
				HttpURLConnection.HTTP_BAD_REQUEST
			);
		} catch(Exception e) {
			return new RsWithStatus(
				new RsJson(
					Json.createObjectBuilder()
					.add("message", e.getLocalizedMessage())
					.build()
				),
				HttpURLConnection.HTTP_INTERNAL_ERROR
			);
		}
		
	}
}
