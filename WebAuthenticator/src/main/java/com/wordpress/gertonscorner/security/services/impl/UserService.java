/**
 * 
 */
package com.wordpress.gertonscorner.security.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
