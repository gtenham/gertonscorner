/**
 * 
 */
package com.wordpress.gertonscorner.security.services;

import java.security.NoSuchAlgorithmException;

import com.wordpress.gertonscorner.security.domain.User;
import com.wordpress.gertonscorner.security.services.exceptions.EncryptionException;

/**
 * Interface authentication service.
 * 
 * @author Gerton
 *
 */
public interface IAuthenticationService {

	/**
	 * Get a new encrypted token for user using 256 bit 
	 * AES encryption in CTR mode.
	 * 
	 * @param user
	 * @return encrypted token
	 * @throws EncryptionException
	 */
	String getTokenForUser(User user) throws EncryptionException;
	
	/**
	 * Render a random string used as token.
	 * 
	 * @return random unique string
	 * @throws NoSuchAlgorithmException 
	 */
	String getRandomToken() throws NoSuchAlgorithmException;
}
