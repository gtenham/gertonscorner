/**
 * 
 */
package com.wordpress.gertonscorner.security.services.impl;

import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wordpress.gertonscorner.security.crypto.SHA;
import com.wordpress.gertonscorner.security.dao.IUserDao;
import com.wordpress.gertonscorner.security.domain.User;
import com.wordpress.gertonscorner.security.services.IUserService;
import com.wordpress.gertonscorner.security.services.exceptions.UserNotFoundException;

/**
 * User service implementation
 * 
 * @author Gerton
 *
 */
@Service("userService")
public class UserService implements IUserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
			
	@Autowired
	private IUserDao userDao;
	
	public UserService() {
	}
	
	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.security.services.IUserService#getUserById(java.lang.String)
	 */
	public User getUserById(String id) throws UserNotFoundException {
		User user = userDao.selectUserById(id);
		if (user == null) {
			throw new UserNotFoundException("User with ID " + id + " could not be found.");
		}
		return user;
	}

	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.security.services.IUserService#getUserByName(java.lang.String)
	 */
	public User getUserByName(String username) throws UserNotFoundException {
		User user = userDao.selectUserByName(username);
		if (user == null) {
			throw new UserNotFoundException("User with name " + username + " could not be found.");
		}
		return user;
	}
	
	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.security.services.IUserService#updatePublicKey(java.lang.String, java.lang.String)
	 */
	public void updatePublicKey(String id, String publicKey) throws UserNotFoundException {
		User user = userDao.selectUserById(id);
		if (user == null) {
			throw new UserNotFoundException("User with ID " + id + " could not be found.");
		}

		try {
			String hashedKey = SHA.getSHA256Digest(publicKey);
			user.setPublicKey(hashedKey);
			userDao.updateUser(user);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
	}

}
