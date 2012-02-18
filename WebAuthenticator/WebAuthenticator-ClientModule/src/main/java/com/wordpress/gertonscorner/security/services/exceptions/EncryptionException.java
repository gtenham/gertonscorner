/**
 * 
 */
package com.wordpress.gertonscorner.security.services.exceptions;

/**
 * @author Gerton
 *
 */
public class EncryptionException extends RuntimeException {

	private static final long serialVersionUID = -2978418560972921972L;

	/**
	 * Default constructor
	 */
	public EncryptionException() {
	}
	
	public EncryptionException(String message) {
		super(message);
	}
}
