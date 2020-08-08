package com.security.authenticator.web;

import org.cactoos.collection.Sticky;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.xe.XeAppend;

/**
 * Take that allows to enter details for user to create.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @author Marcel Brou (broumarcel87@gmail.com)
 */
public final class TkNewUser implements Take {

	@Override
	public Response act(Request req) throws Exception {
				
		return new RsPage(
				"/xsl/create-user.xsl", 
				req, 
				() -> new Sticky<>(
					new XeAppend("menu", "users")
				)
			);
	}
	
}
