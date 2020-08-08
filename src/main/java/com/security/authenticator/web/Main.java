package com.security.authenticator.web;

import java.util.Map;
import java.util.regex.Pattern;

import org.takes.Take;
import org.takes.facets.auth.PsByFlag;
import org.takes.facets.auth.PsChain;
import org.takes.facets.auth.PsCookie;
import org.takes.facets.auth.PsLogout;
import org.takes.facets.auth.TkAuth;
import org.takes.facets.auth.codecs.CcBase64;
import org.takes.facets.auth.codecs.CcCompact;
import org.takes.facets.auth.codecs.CcHex;
import org.takes.facets.auth.codecs.CcSafe;
import org.takes.facets.auth.codecs.CcSalted;
import org.takes.facets.auth.codecs.CcXor;
import org.takes.facets.fork.FkFixed;
import org.takes.facets.fork.FkParams;
import org.takes.facets.fork.TkFork;
import org.takes.http.Exit;
import org.takes.http.FtCli;
import org.takes.tk.TkRedirect;

import com.minlessika.tk.TkWithCookieDomain;
import com.minlessika.utils.ConsoleArgs;

/**
 * Entry of application
 * 
 * @author Olivier B. OURA (baudoliver7@gmail.com)
 *
 */
public final class Main {

	/**
	 * Pass phrase
	 */
	public static final String PASS_PHRASE = "My faith is in Jesus-Christ !";
	
	/**
	 * App entry
	 * @param args Arguments
	 * @throws Exception If some problems in
	 */
	public static void main(String[] args) throws Exception {
		
		final Map<String, String> argsMap = new ConsoleArgs("--", args).asMap();
		
		final String suratiUrl;
		if(!argsMap.containsKey("surati-url"))
			throw new IllegalArgumentException("You need to set surati-url parameter !");
		
		suratiUrl = argsMap.get("surati-url");
		
		final String domain;
		if(!argsMap.containsKey("domain"))
			throw new IllegalArgumentException("You need to set domain parameter !");
		
		domain = argsMap.get("domain");
		
		new FtCli(
			new TkWithCookieDomain(
				auth(
					new TkApp(suratiUrl)
				), 
				domain
			),
			args
		).start(Exit.NEVER);
		
	}
	
	/**
	 * Applies user authentication rules on take
	 * @param take Take
	 * @return Take with user authentication rules applied
	 */
	private static Take auth(final Take take) {
		return new TkAuth(
			new TkFork(
				new FkParams(
					PsByFlag.class.getSimpleName(), 
					Pattern.compile(".+"), 
					new TkRedirect("/")),
				new FkFixed(take)
			), 
			new PsChain(
				new PsByFlag(
					new PsByFlag.Pair(
						PsLogout.class.getSimpleName(), 
						new PsLogout()
					)
				),
				new PsCookie(
						new CcBase64(
							new CcSafe(
				            	new CcHex(
				                    new CcXor(
				                        new CcSalted(new CcCompact()),
				                        PASS_PHRASE
				                    )
				                )
							)
						)
				)
			)
		);
	}
    
}
