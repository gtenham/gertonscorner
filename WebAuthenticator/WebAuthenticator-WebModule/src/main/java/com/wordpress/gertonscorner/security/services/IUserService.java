/**
 * 
 */
package com.wordpress.gertonscorner.security.services;

import com.wordpress.gertonscorner.security.domain.User;
import com.wordpress.gertonscorner.security.services.exceptions.UserNotFoundException;

/**
 * Interface User service
 * 
 * @author Gerton
 *
 */
public interface IUserService {

	/**
	 * Get User by given id.
	 * 
	 * @param id
	 * @return User
	 * @throws UserNotFoundException
	 */
	User getUserById(String id) throws UserNotFoundException;
	
	/**
	 * Get User by given username.
	 * 
	 * @param username
	 * @return User
	 * @throws UserNotFoundException
	 */
	User getUserByName(String username) throws UserNotFoundException;
	
	/**
	 * Update public key for given user
	 * 
	 * @param id
	 * @param publicKey
	 * @throws UserNotFoundException
	 */
	void updatePublicKey(String id, String publicKey) throws UserNotFoundException;
}
