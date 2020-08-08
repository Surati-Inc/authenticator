package com.security.authenticator.users;

/**
 * Person
 * 
 * @author Olivier B. OURA (baudolivier.oura@gmail.com)
 *
 */
public interface Person {
	
	/**
	 * Get identifier of a person
	 * @return identifier
	 */
	Long id();
	
	/**
	 * Full name of a person
	 * @return name
	 */
	String name();
	
	/**
	 * Modify name of a person
	 * @param name
	 */
	void update(String name);
}
