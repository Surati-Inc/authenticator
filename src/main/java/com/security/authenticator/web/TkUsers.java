package com.security.authenticator.web;

import java.util.Collection;
import java.util.LinkedList;

import org.cactoos.collection.Sticky;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeDirectives;
import org.takes.rs.xe.XeSource;
import org.xembly.Directives;

import com.minlessika.db.Database;
import com.security.authenticator.users.DbUsers;
import com.security.authenticator.users.User;

/**
 * Take that displays all users.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @author Marcel Brou (broumarcel87@gmail.com)
 */
public final class TkUsers implements Take {

	/**
	 * Database
	 */
	private final Database database;
	
	/**
	 * Ctor.
	 * @param database Database
	 */
	public TkUsers(final Database database) {
		this.database = database;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		
		final Iterable<User> users = new DbUsers(database).iterate();
		final Collection<XeSource> userSources = new LinkedList<>();
		for (User user : users) {
			userSources.add(
				new XeDirectives(
					new Directives().add("user")
									.add("login").set(user.login().toString()).up()
									.add("name").set(user.name().toString()).up()
									.add("blocked").set(user.blocked()).up()
									.up()
				)
			);
		}
		
		return new RsPage(
			"/xsl/users.xsl", 
			req, 
			() -> new Sticky<>(
				new XeAppend("menu", "users"),
				new XeAppend("users", new XeChain(userSources))
			)
		);
	}

}
