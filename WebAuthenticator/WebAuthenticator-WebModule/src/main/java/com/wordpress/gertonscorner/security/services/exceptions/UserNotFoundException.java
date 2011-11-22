/**
 * 
 */
package com.wordpress.gertonscorner.security.services.exceptions;

/**
 * @author Gerton
 *
 */
public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2700636865541963512L;

	/**
	 * Default constructor
	 */
	public UserNotFoundException() {
	}
	
	public UserNotFoundException(String message) {
		super(message);
	}

}
