package com.security.authenticator.webservices;

import java.net.HttpURLConnection;

import javax.json.Json;
import javax.json.JsonObject;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqGreedy;
import org.takes.rs.RsJson;
import org.takes.rs.RsWithStatus;

import com.security.authenticator.encryption.JwtToken;
import com.security.authenticator.encryption.Token;
import com.security.authenticator.rq.RqJson;
import com.security.authenticator.rq.RqJsonBase;
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
	
	@Override
	public Response act(Request req) throws Exception {
		
		final RqJson rqJson = new RqJsonBase(new RqGreedy(req));
		JsonObject payload = rqJson.payload().asJsonObject();
		
		final String token = payload.getString("token");
		
		try {
			final Token jwtToken = new JwtToken(token, Main.PASS_PHRASE);
			final Claims claims = jwtToken.claims();
			final String login = claims.getSubject();
			final String issuer = claims.getIssuer();
			
			if(login.equals("user") && issuer.equals("Authenticator")) {
				return new RsWithStatus(HttpURLConnection.HTTP_NO_CONTENT);
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
		}
		
	}
}
