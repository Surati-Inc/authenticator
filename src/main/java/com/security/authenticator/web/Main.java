package com.security.authenticator.web;

import org.takes.http.Exit;
import org.takes.http.FtCli;

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
		
		new FtCli(
			new TkApp(),
			args
		).start(Exit.NEVER);
		
	}
    
}
