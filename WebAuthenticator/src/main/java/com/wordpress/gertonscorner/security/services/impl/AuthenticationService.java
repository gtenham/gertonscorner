/**
 * 
 */
package com.wordpress.gertonscorner.security.services.impl;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wordpress.gertonscorner.security.crypto.AESCtr;
import com.wordpress.gertonscorner.security.crypto.SHA;
import com.wordpress.gertonscorner.security.dao.IRegisteredServiceDao;
import com.wordpress.gertonscorner.security.domain.User;
import com.wordpress.gertonscorner.security.services.IAuthenticationService;
import com.wordpress.gertonscorner.security.services.ISessionService;
import com.wordpress.gertonscorner.security.services.IUserService;
import com.wordpress.gertonscorner.security.services.exceptions.EncryptionException;
import com.wordpress.gertonscorner.security.services.exceptions.ProxyAuthenticationRequiredException;

/**
 * Authentication service implementation.
 * 
 * @author Gerton
 *
 */
@Service("authenticationService")
public class AuthenticationService implements IAuthenticationService {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ISessionService sessionService;
	
	@Autowired
	IRegisteredServiceDao registeredServiceDao;
	
	public AuthenticationService() {
	}
	
	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.security.services.IAuthenticationService#getTokenForUser(java.lang.String)
	 */
	public String getTokenForUser(User user) throws EncryptionException {
		String encryptedToken = null;
		
		try {
			String token = getRandomToken();
			encryptedToken = AESCtr.encrypt256(user.getPublicKey(), token);
			sessionService.setUserToken(user.getUserName(), encryptedToken);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new EncryptionException(e.getMessage());
		}
		
		return encryptedToken;
	}

	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.security.services.IAuthenticationService#getServiceTicket(com.wordpress.gertonscorner.security.domain.User, java.lang.String)
	 */
	public String getServiceTicket(User user, String service, String remoteAddress) throws EncryptionException {
		String decryptedUserToken = null;
		String decryptedServiceRequest = null;
		String encryptedServiceTicket = null;
		
		try {
			// Obtain/decrypt user token from session
			decryptedUserToken = AESCtr.decrypt256(user.getPublicKey(),sessionService.getUserToken(user.getUserName()));
			decryptedServiceRequest = AESCtr.decrypt256(decryptedUserToken,service);
			// Obtain the secret service key from datasource
			String serviceKey = registeredServiceDao.getServiceById(decryptedServiceRequest).getServiceAuthentication();
			// Create a new service ticket for user
			encryptedServiceTicket = AESCtr.encrypt256(serviceKey, decryptedServiceRequest+","+remoteAddress);
			// Enrich user session with remote address and encrypted service ticket
			sessionService.setRemoteAddress(user.getUserName(), remoteAddress);
			sessionService.setServiceToken(user.getUserName(), encryptedServiceTicket);
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			sessionService.destroySession(user.getUserName());
			throw new EncryptionException(e.getMessage());
		}
		return encryptedServiceTicket;
	}

	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.security.services.IAuthenticationService#validateAuthentication(java.lang.String, java.lang.String)
	 */
	public void validateAuthentication(String servicetoken, String remoteAddress, String serviceName, Boolean remoteAddressOnly) throws ProxyAuthenticationRequiredException {
		if (servicetoken != null && remoteAddress != null) {
			String[] credentials = servicetoken.split(":");
			parseServiceRequest(credentials[0], credentials[1], remoteAddress, serviceName, remoteAddressOnly);
		} else {
			throw new ProxyAuthenticationRequiredException("WAS authentication required!");
		}
	}
	
	/**
	 * Parses and validates the service request provided.
	 * 
	 * @param username - Provided by client
	 * @param serviceToken - provided by client
	 * @param remoteAddress - Obtained from request by server
	 * @param serviceName - Provided by authentication annotation
	 * @param remoteAddressOnly - Provided by authentication annotation
	 * @throws ProxyAuthenticationRequiredException
	 */
	private void parseServiceRequest(String username, String serviceToken, String remoteAddress, String serviceName, Boolean remoteAddressOnly) throws ProxyAuthenticationRequiredException {
		if (username == null || serviceToken == null) {
			throw new ProxyAuthenticationRequiredException("WAS authentication required!");
		}
		final User user = userService.getUserByName(username);
		final String sessionUserToken = sessionService.getUserToken(user.getUserName());
		final String serviceKey = registeredServiceDao.getServiceById(serviceName).getServiceAuthentication();
		String decryptedUserToken = null;
		String decryptedServiceRequest[] = null;
		String decryptedServiceTicket[] = null;
		 
		try {
			// Decrypt user token with user public key from datasource
			decryptedUserToken = AESCtr.decrypt256(user.getPublicKey(), sessionUserToken);
			// Decrypt service request with user token as key
			decryptedServiceRequest = AESCtr.decrypt256(decryptedUserToken,serviceToken).split(":");
			// Decrypt service ticket with key from datasource
			decryptedServiceTicket = AESCtr.decrypt256(serviceKey, decryptedServiceRequest[1]).split(",");
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			sessionService.destroySession(user.getUserName());
			throw new ProxyAuthenticationRequiredException("WAS authentication required!");
		}
		
		// Validate remote addresses and service names
		if (!equalsServiceName(serviceName, decryptedServiceTicket[0]) ||
			!equalsRemoteAddress(remoteAddress, decryptedServiceTicket[1])
			) 
		{
			throw new ProxyAuthenticationRequiredException("WAS authentication required!");
		}
	}
	
	private boolean equalsServiceName(final String serviceName, final String serviceNameByRequest) {
		return serviceName.equalsIgnoreCase(serviceNameByRequest);
	}
	
	private boolean equalsRemoteAddress(final String remoteAddressRequest, final String remoteAddressTicket) {
		return remoteAddressRequest.equalsIgnoreCase(remoteAddressTicket);
	}
	
	/**
	 * Render a random string used as token.
	 * 
	 * @return random unique string
	 * @throws NoSuchAlgorithmException 
	 */
	private String getRandomToken() throws NoSuchAlgorithmException {
		String uuid = UUID.randomUUID().toString();
		String token = SHA.getSHA256Digest(uuid);
		logger.info(token);
		return token;
	}
}
