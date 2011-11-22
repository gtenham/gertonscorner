/**
 * 
 */
package com.wordpress.gertonscorner.security.services;

/**
 * @author Gerton
 *
 */
public interface ISessionService {

	/**
	 * Set user authentication token
	 * 
	 * @param username
	 * @param token
	 */
	void setUserToken(String username, String token);
	
	
	/**
	 * Get user authentication token
	 * 
	 * @param username
	 * @return
	 */
	String getUserToken(String username);
	
	/**
	 * Set service request token for user
	 * 
	 * @param username
	 * @param token
	 */
	void setServiceToken(String username, String token);
	
	/**
	 * Set remote address for user.
	 * 
	 * @param username
	 * @param remoteAddress
	 */
	void setRemoteAddress(String username, String remoteAddress);
	
	/**
	 * Get remote address for user.
	 * 
	 * @param username
	 * @return remoteAddress
	 */
	String getRemoteAddress(String username);
	
	/**
	 * Destroy user session
	 * 
	 * @param username
	 */
	void destroySession(String username);
}
