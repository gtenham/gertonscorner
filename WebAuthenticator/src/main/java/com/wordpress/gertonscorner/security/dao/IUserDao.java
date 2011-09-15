/**
 * 
 */
package com.wordpress.gertonscorner.security.dao;

import com.wordpress.gertonscorner.security.domain.User;

/**
 * Interface UserDAO
 * 
 * @author Gerton
 *
 */
public interface IUserDao {

	/**
	 * Select a user by id
	 * 
	 * @param id
	 * @return User
	 */
	User selectUserById(String id);
	
	/**
	 * Insert a new user
	 * 
	 * @param user
	 */
	void insertUser(User user);
	
	/**
	 * Delete an existing user
	 * 
	 * @param id
	 */
	void deleteUser(String id);
	
	/**
	 * Update an existing user
	 * 
	 * @param user
	 */
	void updateUser(User user);
}
