/**
 * 
 */
package com.wordpress.gertonscorner.security.services.impl;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wordpress.gertonscorner.security.crypto.AESCtr;
import com.wordpress.gertonscorner.security.crypto.SHA;
import com.wordpress.gertonscorner.security.domain.User;
import com.wordpress.gertonscorner.security.services.IAuthenticationService;
import com.wordpress.gertonscorner.security.services.exceptions.EncryptionException;

/**
 * Authentication service implementation.
 * 
 * @author Gerton
 *
 */
@Service("authenticationService")
public class AuthenticationService implements IAuthenticationService {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	
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
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new EncryptionException(e.getMessage());
		}
		
		return encryptedToken;
	}

	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.security.services.IAuthenticationService#getRandomToken()
	 */
	public String getRandomToken() throws NoSuchAlgorithmException {
		//String token = "The quick brown fox jumps over the lazy dog."; //"søme highly secret text to be encrypteđ";
		String uuid = UUID.randomUUID().toString();
		String token = SHA.getSHA256Digest(uuid);
		logger.info(token);
		System.out.println("Token: "+token);
		return token;
	}

}
