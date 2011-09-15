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
}
