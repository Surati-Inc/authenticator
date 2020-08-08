package com.security.authenticator.web;

import org.cactoos.collection.Sticky;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rq.RqHref;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeDirectives;
import org.takes.rs.xe.XeSource;
import org.xembly.Directives;

import com.minlessika.db.Database;
import com.security.authenticator.users.DbUsers;
import com.security.authenticator.users.User;

/**
 * Take that edits the password of an user.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @author Marcel Brou (broumarcel87@gmail.com)
 */
public final class TkEditUserPassword implements Take {
	
	/**
	 * Database
	 */
	private final Database database;
	
	/**
	 * Ctor.
	 * @param database Database
	 */
	public TkEditUserPassword(final Database database) {
		this.database = database;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		
		final String login = new RqHref.Smart(req).single("login");
		
		final User user = new DbUsers(database).get(login);
		
		final XeSource userSource = new XeDirectives(
										new Directives().add("user")
										.add("login").set(user.login()).up()
										.add("password").set(user.password()).up()
										.up()
									);
		
		return new RsPage(
			"/xsl/edit-user-password.xsl", 
			req, 
			() -> new Sticky<>(
				new XeAppend("menu", "users"),
				userSource
			)
		);
	}

}
