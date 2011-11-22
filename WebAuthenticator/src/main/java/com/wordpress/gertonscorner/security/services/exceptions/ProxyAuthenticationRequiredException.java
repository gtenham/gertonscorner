/**
 * 
 */
package com.wordpress.gertonscorner.security.services.exceptions;

/**
 * @author Gerton
 *
 */
public class ProxyAuthenticationRequiredException extends RuntimeException {

	private static final long serialVersionUID = 7225462155634966193L;

	/**
	 * Default constructor
	 */
	public ProxyAuthenticationRequiredException() {
	}
	
	public ProxyAuthenticationRequiredException(String message) {
		super(message);
	}
}
