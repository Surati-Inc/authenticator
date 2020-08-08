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

import com.minlessika.db.Database;
import com.minlessika.db.TkTransaction;
import com.minlessika.secure.TkAnonymous;
import com.minlessika.secure.TkSecure;
import com.security.authenticator.webservices.TkBlockUser;
import com.security.authenticator.webservices.TkChangeUserPassword;
import com.security.authenticator.webservices.TkSignin;
import com.security.authenticator.webservices.TkSignup;
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
	public TkApp(final Database database, final String suratiUrl) {
		super(make(database, suratiUrl));
	}

	/**
	 * Ctor.
	 * @return Takes
	 */
	private static Take make(final Database database, final String suratiUrl) {
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
									"/login", 
									new TkAnonymous(new TkLogin(suratiUrl))
								),
								new FkRegex(
									"/token", 
									new TkFork(
										new FkMethods("POST", new TkSignin(database))
									)
								),
								new FkRegex(
									"/token/validate", 
									new TkFork(
										new FkMethods("POST", new TkValidateToken(database))
									)
								),
									new FkRegex(
										"/users", 
										new TkSecure(
											new TkUsers(database)
										)
									),
									new FkRegex(
										"/user/visualize", 
										new TkSecure(
											new TkVisualizeUser(database)
										)
									),
									new FkRegex(
										"/user/new", 
										new TkSecure(
											new TkNewUser()
										)
									),
									new FkRegex(
										"/user/register", 
										new TkSecure(
											new TkTransaction(
												new TkRegisterUser(database), 
												database
											)
										)
									),
									new FkRegex(
										"/user/name/edit", 
										new TkSecure(
											new TkEditUserName(database)
										)
									),
									new FkRegex(
										"/user/password/edit", 
										new TkSecure(
											new TkEditUserPassword(database)
										)
									),
									new FkRegex(
										"/user/name/save", 
										new TkSecure(
											new TkTransaction(
												new TkSaveUserName(database), 
												database
											)
										)
									),
									new FkRegex(
										"/user/password/save", 
										new TkSecure(
											new TkTransaction(
												new TkSaveUserPassword(database), 
												database
											)
										)
									),
									new FkRegex(
										"/user/block", 
										new TkSecure(
											new TkTransaction(
												new TkBlockUser(database), 
												database
											)
										)
									),
									new FkRegex(
										"/api/user", 
										new TkFork(
											new FkMethods(
												"POST", 
												new TkSecure(
													new TkTransaction(
														new TkSignup(database), 
														database
													)
												)
											)
										)										
									),
									new FkRegex(
										"/api/user/password", 
										new TkFork(
											new FkMethods(
												"POST", 
												new TkSecure(
													new TkTransaction(
														new TkChangeUserPassword(database), 
														database
													)
												)
											)
										)										
									),
									new FkRegex(
										"/api/user/block", 
										new TkFork(
											new FkMethods(
												"POST", 
												new TkSecure(
													new TkTransaction(
														new TkBlockUser(database), 
														database
													)
												)
											)
										)										
									)								
							)
						)
					)
				);
	}
}
