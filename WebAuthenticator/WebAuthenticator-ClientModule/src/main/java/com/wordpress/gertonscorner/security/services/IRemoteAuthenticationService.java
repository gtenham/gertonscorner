package com.wordpress.gertonscorner.security.services;

import com.wordpress.gertonscorner.security.services.exceptions.ProxyAuthenticationRequiredException;

/**
 * Interface remote authentication service.
 * 
 * @author Gerton
 *
 */
public interface IRemoteAuthenticationService {

	/**
	 * Validate authentication
	 * 
	 * @param servicetoken
	 * @param remoteAddress
	 * @param serviceName
	 * @param remoteAddressOnly
	 * @throws ProxyAuthenticationRequiredException
	 */
	void validateAuthentication(String servicetoken, String remoteAddress, String serviceName, Boolean remoteAddressOnly) throws ProxyAuthenticationRequiredException;
	
}
