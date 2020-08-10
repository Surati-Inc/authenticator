package com.security.authenticator.webservices;

import java.net.HttpURLConnection;
import java.sql.Date;
import java.time.LocalDate;

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
import com.security.authenticator.users.DbUsers;
import com.security.authenticator.users.Users;
import com.security.authenticator.web.Main;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * Take that generates a token.
 * 
 * <p>The class is immutable and thread-safe.</p>
 * 
 * @author Olivier B. OURA (baudolivier.oura@gmail.com)
 *
 */

public class TkSignin implements Take {
	
	private final Database source;
	
	public TkSignin(final Database source) {
		this.source = source;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		
		final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
		
		final String login = form.single("login");
		final String password = form.single("password");
		
		try {
			final Users users = new DbUsers(source);
			users.authenticate(login, password);
			
			final Claims claims = Jwts.claims()
					  .setIssuedAt(Date.valueOf(LocalDate.now()))
					  .setIssuer("Authenticator")
					  .setSubject(login);

final String token = new JwtToken(claims, Main.PASS_PHRASE).toString();

return new RsWithStatus(
		new RsJson(
			Json.createObjectBuilder()
			.add("token", token)
			.build()
		), 
		HttpURLConnection.HTTP_CREATED
);			
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
