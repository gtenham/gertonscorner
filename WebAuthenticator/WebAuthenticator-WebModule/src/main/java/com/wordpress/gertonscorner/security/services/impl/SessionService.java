/**
 * 
 */
package com.wordpress.gertonscorner.security.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wordpress.gertonscorner.security.dao.ISessionDao;
import com.wordpress.gertonscorner.security.domain.Session;
import com.wordpress.gertonscorner.security.services.ISessionService;

/**
 * @author Gerton
 *
 */
@Service("sessionService")
public class SessionService implements ISessionService {

	@Autowired
	ISessionDao sessionDao;
	
	public SessionService() {}
	
	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.security.services.ISessionService#setUserToken(java.lang.String, java.lang.String)
	 */
	public void setUserToken(String username, String token) {
		Boolean sessionExists = true;
		Session userSession = sessionDao.getUserSession(username);
		if (userSession == null ) {
			sessionExists = false;
			userSession = new Session();
		}
		userSession.setUserName(username);
		userSession.setUserToken(token);
		writeSession(userSession, sessionExists);
	}

	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.security.services.ISessionService#getUserToken(java.lang.String)
	 */
	public String getUserToken(String username) {
		Session userSession = sessionDao.getUserSession(username);
		return userSession.getUserToken();
	}
	
	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.security.services.ISessionService#setServiceToken(java.lang.String, java.lang.String)
	 */
	public void setServiceToken(String username, String token) {
		Session userSession = sessionDao.getUserSession(username);
		if (userSession == null ) {
			return;
		}
		userSession.setServiceToken(token);
		writeSession(userSession, true);
	}

	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.security.services.ISessionService#destroySession(java.lang.String)
	 */
	public void destroySession(String username) {
		sessionDao.removeUserSession(username);
	}
	
	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.security.services.ISessionService#setRemoteAddress(java.lang.String, java.lang.String)
	 */
	public void setRemoteAddress(String username, String remoteAddress) {
		Session userSession = sessionDao.getUserSession(username);
		if (userSession == null ) {
			return;
		}
		userSession.setRemoteAddress(remoteAddress);
		writeSession(userSession, true);
	}
	
	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.security.services.ISessionService#getRemoteAddress(java.lang.String)
	 */
	public String getRemoteAddress(String username) {
		return sessionDao.getUserSession(username).getRemoteAddress();
	}

	private void writeSession(Session usersession, Boolean exists) {
		if (exists) {
			sessionDao.updateUserSession(usersession);
		} else {
			sessionDao.createUserSession(usersession);
		}
	}
	
}
