package com.security.authenticator.web;

import java.util.logging.Level;

import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.facets.flash.RsFlash;
import org.takes.facets.forward.RsForward;
import org.takes.rq.RqGreedy;
import org.takes.rq.form.RqFormSmart;

import com.minlessika.db.Database;
import com.security.authenticator.users.DbUsers;
import com.security.authenticator.users.User;

/**
 * Take that modifies an user's name.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @author Marcel Brou (broumarcel87@gmail.com)
 */

public final class TkSaveUserName implements Take {

	/**
	 * Database
	 */
	private final Database database;
	
	/**
	 * Ctor.
	 * @param database Database
	 */
	public TkSaveUserName(final Database database) {
		this.database = database;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		
		final RqFormSmart form = new RqFormSmart(new RqGreedy(req));		
		
		final String login = form.single("login");
		final User user = new DbUsers(database).get(login);
		
		final String name = form.single("name");
		
		user.update(name);
		
		return new RsForward(
			new RsFlash(
				"User name has been saved with success !",
				Level.INFO
			),
			"/user/visualize?login=" + login
		);	
	}
}
