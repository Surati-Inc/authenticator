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
import com.security.authenticator.users.Users;

/**
 * Take that creates an user.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @author Marcel Brou (broumarcel87@gmail.com)
 */

public final class TkRegisterUser implements Take {

	/**
	 * Database
	 */
	private final Database database;
	
	/**
	 * Ctor.
	 * @param database Database
	 */
	public TkRegisterUser(final Database database) {
		this.database = database;
	}
	
	@Override
	public Response act(Request req) throws Exception {
		
		final RqFormSmart form = new RqFormSmart(new RqGreedy(req));
		
		final String login = form.single("login");
		final String name = form.single("name");
		final String password = "password";
		
		final Users users = new DbUsers(database);
		users.register(name, login, password);
		
		return new RsForward(
			new RsFlash(
				String.format("User %s has been created with success !", name),
				Level.INFO
			),
			"/user/visualize?login=" + login
		);	
	}
}
