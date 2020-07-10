package com.security.authenticator.web;

import org.takes.Take;
import org.takes.facets.flash.TkFlash;
import org.takes.facets.fork.FkMethods;
import org.takes.facets.fork.FkRegex;
import org.takes.facets.fork.TkFork;
import org.takes.facets.forward.TkForward;
import org.takes.tk.TkClasspath;
import org.takes.tk.TkSlf4j;
import org.takes.tk.TkWithType;
import org.takes.tk.TkWrap;

import com.security.authenticator.webservices.TkSignin;
import com.security.authenticator.webservices.TkValidateToken;

/**
 * App.
 * 
 * @author Olivier B. OURA (baudoliver7@gmail.com)
 *
 */
public final class TkApp extends TkWrap {

	/**
	 * Ctor.
	 */
	public TkApp() {
		super(make());
	}

	/**
	 * Ctor.
	 * @return Takes
	 */
	private static Take make() {
		return new TkSlf4j(
				new TkForward(
						new TkFlash(
							new TkFork(
								new FkRegex(
									"/css/.+", 
									new TkWithType(new TkClasspath(), "text/css")
								),
								new FkRegex(
									"/img/[a-z]+\\.svg", 
									new TkWithType(new TkClasspath(), "image/svg+xml")
								),
								new FkRegex(
									"/img/[a-z]+\\.png", 
									new TkWithType(new TkClasspath(), "image/png")
								),
								new FkRegex("/robots\\.txt", ""),
								new FkRegex("/", new TkIndex()),
								new FkRegex(
									"/token", 
									new TkFork(
										new FkMethods("POST", new TkSignin())
									)
								),
								new FkRegex(
									"/token/validate", 
									new TkFork(
										new FkMethods("POST", new TkValidateToken())
									)
								)
							)
						)
					)
				);
	}
}
