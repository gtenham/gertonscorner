/**
 * 
 */
package com.wordpress.gertonscorner.security.services;

import com.wordpress.gertonscorner.security.domain.User;
import com.wordpress.gertonscorner.security.services.exceptions.EncryptionException;
import com.wordpress.gertonscorner.security.services.exceptions.ProxyAuthenticationRequiredException;

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
	 * Get a new service ticket for user using 256 bit 
	 * AES encryption in CTR mode.
	 * 
	 * @param user
	 * @param service
	 * @param remoteAddress
	 * @return encrypted ticket
	 * @throws EncryptionException
	 */
	String getServiceTicket(User user, String service, String remoteAddress) throws EncryptionException;
	
	/**
	 * Validate authentication
	 * 
	 * @param servicetoken
	 * @param remoteAddress
	 * @param serviceName
	 * @param remoteAddressOnly
	 * @throws ProxyAuthenticationRequiredException
	 */
	//void validateAuthentication(String servicetoken, String remoteAddress, String serviceName, Boolean remoteAddressOnly) throws ProxyAuthenticationRequiredException;
	
	
	/**
	 * Invalidate the session for given user
	 * 
	 * @param user
	 * @throws ProxyAuthenticationRequiredException
	 */
	void invalidateSessionForUser(User user);
}
