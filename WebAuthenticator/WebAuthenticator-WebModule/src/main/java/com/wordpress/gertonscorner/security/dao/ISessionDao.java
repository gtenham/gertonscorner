/**
 * 
 */
package com.wordpress.gertonscorner.security.dao;

import com.wordpress.gertonscorner.security.domain.Session;

/**
 * @author Gerton
 *
 */
public interface ISessionDao {
	
	Session getUserSession(String username);
	
	void createUserSession(Session session);
	
	void updateUserSession(Session session);
	
	void removeUserSession(String username);

}
