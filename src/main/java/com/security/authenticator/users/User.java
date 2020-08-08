package com.security.authenticator.users;

/**
 * User
 * 
 * @author Olivier B. OURA (baudolivier.oura@gmail.com)
 *
 */
public interface User extends Person {
	
	/**
	 * Login of a user
	 * @return login
	 */
	String login();
	
	/**
	 * Password of a user
	 * @return password
	 */
	String password();
	
	/**
	 * Force password changing
	 * @param newPassword new password
	 */
	void forceChangePassword(String newPassword);
	
	/**	
	 * If user is blocked
	 * @return blocked or not
	 */
	boolean blocked();
	
	/**
	 * Block or not user
	 * @param enable Blocked or not
	 */
	void block(boolean enable);
}
