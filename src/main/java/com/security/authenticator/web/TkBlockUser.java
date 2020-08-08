package com.security.authenticator.web;

import java.util.logging.Level;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqHref;
import org.takes.rq.RqHref.Smart;

import com.minlessika.db.Database;
import com.security.authenticator.users.DbUsers;
import com.security.authenticator.users.User;

/**
 * Take that blocks or unblocks an user.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @author Marcel Brou (broumarcel87@gmail.com)
 */
public final class TkBlockUser implements Take {

	/**
	 * Database
	 */
	private final Database database;
	
	/**
	 * Ctor.
	 * @param database Database
	 */
	public TkBlockUser(final Database database) {
		this.database = database;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		
		final Smart href = new RqHref.Smart(req);
		
		final String login = href.single("login");
		
		final boolean enable = Boolean.parseBoolean(
									href.single("enable")
							   );
		
		final User user = new DbUsers(database).get(login);
		user.block(enable);
		
		final String message;
		if(enable) {
			message = String.format("User %s has been blocked with success !", login);
		} else {
			message = String.format("User %s has been unblocked with success !", login);
		}
		
		return new RsForward(
			new RsFlash(
				message,
				Level.INFO
			),
			"/user/visualize?login=" + login
		);	
	}

}
