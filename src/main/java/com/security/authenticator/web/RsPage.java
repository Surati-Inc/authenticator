package com.security.authenticator.web;

import java.io.IOException;
import java.util.Collections;

import org.takes.Request;
import org.takes.Response;
import org.takes.Scalar;
import org.takes.facets.auth.Identity;
import org.takes.facets.auth.RqAuth;
import org.takes.facets.auth.XeIdentity;
import org.takes.facets.auth.XeLogoutLink;
import org.takes.facets.flash.XeFlash;
import org.takes.facets.fork.FkTypes;
import org.takes.facets.fork.RsFork;
import org.takes.rs.RsWithType;
import org.takes.rs.RsWrap;
import org.takes.rs.RsXslt;
import org.takes.rs.xe.RsXembly;
import org.takes.rs.xe.XeAppend;
import org.takes.rs.xe.XeChain;
import org.takes.rs.xe.XeDate;
import org.takes.rs.xe.XeMemory;
import org.takes.rs.xe.XeMillis;
import org.takes.rs.xe.XeSource;
import org.takes.rs.xe.XeStylesheet;
import org.takes.rs.xe.XeWhen;

/**
 * Index resource, front page of the website.
 *
 * @author Olivier B. OURA (baudoliver7@gmail.com)
 */
public final class RsPage extends RsWrap {

	RsPage(final String xsl, final Request req, final Scalar<Iterable<XeSource>> src) throws IOException {
		super(RsPage.make(xsl, req, src));
	}
	
	RsPage(final String xsl, final Request req) throws IOException {
		super(RsPage.make(xsl, req, Collections::emptyList));
	}
	
	private static Response make(final String xsl, final Request req, final Scalar<Iterable<XeSource>> src) throws IOException {
		
		final Response raw = new RsXembly(
			new XeStylesheet(xsl),
			new XeAppend(
				"page", 
				new XeMillis(false),
				new XeChain(src),
				new XeMemory(),
				new XeDate(),
				new XeFlash(req),
				new XeWhen(
					!new RqAuth(req).identity().equals(Identity.ANONYMOUS), 
					new XeChain(
						new XeIdentity(req), 
						new XeLogoutLink(req)
					)
				),
                new XeAppend(
                    "version",
                    new XeAppend("name", "0.0.1")
                ),
                new XeMillis(true)
			)
		);
		
		return new RsFork(
            req,
            new FkTypes(
                "*/*",
                new RsXslt(new RsWithType(raw, "text/html"))
            )
        );
	}

}
