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
	 * Select a user by its username
	 * 
	 * @param username
	 * @return User
	 */
	User selectUserByName(String username);
	
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
