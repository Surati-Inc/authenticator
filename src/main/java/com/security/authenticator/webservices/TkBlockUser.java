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
import com.security.authenticator.users.DbUsers;
import com.security.authenticator.users.User;
import com.security.authenticator.users.Users;

/**
 * Take that change accessibility of an user.
 * 
 * <p>The class is immutable and thread-safe.</p>
 * 
 * @author Olivier B. OURA (baudolivier.oura@gmail.com)
 *
 */

public class TkBlockUser implements Take {
	
	private final Database source;
	
	public TkBlockUser(final Database source) {
		this.source = source;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		
		final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
		
		final String login = form.single("login");
		final boolean enable = Boolean.parseBoolean(form.single("enable"));
		
		try {
			
			final Users users = new DbUsers(source);
			final User user = users.get(login);
			
			user.block(enable);
			
			return new RsWithStatus(
					new RsJson(
						Json.createObjectBuilder()
						.add("message", "Accessibility has been applied on user with success !")
						.build()
					), 
					HttpURLConnection.HTTP_OK
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
