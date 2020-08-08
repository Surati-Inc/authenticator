package com.security.authenticator.users;

/**
 * Users who manage application
 * 
 * @author Olivier B. OURA (baudolivier.oura@gmail.com)
 *
 */
public interface Users {
	
	/**
	 * Check a user credentials
	 * @param login
	 * @param password
	 * @return boolean
	 */
	boolean authenticate(String login, String password);
	
	/**
	 * Checks if a user with login exists
	 * @param login
	 * @return boolean exits
	 */
	boolean has(String login);
	
	/**
	 * Get User
	 * @param login
	 * @return User
	 */
	User get(String login);
	
	/**
	 * Get User
	 * @param id
	 * @return User
	 */
	User get(Long id);
	
	/**
	 * Add a new user
	 * @param name
	 * @param login
	 * @param password
	 */
	void register(String name, String login, String password);
	
	/**
	 * Iterate item
	 * @return All users
	 */
	Iterable<User> iterate();
}
