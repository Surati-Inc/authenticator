package com.security.authenticator.web;

import org.cactoos.collection.Sticky;
import org.takes.Request;
import org.takes.Response;
import org.takes.Take;
import org.takes.rs.xe.XeAppend;

/**
 * Take index.
 *
 * <p>The class is immutable and thread-safe.</p>
 *
 * @author Olivier B. OURA (baudoliver7@gmail.com)
 */
public final class TkIndex implements Take {

	@Override
	public Response act(Request req) throws Exception {
		
		return new RsPage(
			"/xsl/index.xsl", 
			req, 
			() -> new Sticky<>(
				new XeAppend("menu", "index")
			)
		);
	}

}
