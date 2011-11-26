package com.wordpress.gertonscorner.security.domain;

import java.io.Serializable;
import java.util.Date;

public class Session implements Serializable {
	
	private static final long serialVersionUID = 7328066904065206366L;
	
	private String userName;
	private String userToken;
	private String serviceToken;
	private String remoteAddress;
	private Date lastActiveDate;
	
	public Session() {}
	
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userToken the userToken to set
	 */
	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	/**
	 * @return the userToken
	 */
	public String getUserToken() {
		return userToken;
	}

	/**
	 * @param serviceToken the serviceToken to set
	 */
	public void setServiceToken(String serviceToken) {
		this.serviceToken = serviceToken;
	}

	/**
	 * @return the serviceToken
	 */
	public String getServiceToken() {
		return serviceToken;
	}

	/**
	 * @param remoteAddress the remoteAddress to set
	 */
	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	/**
	 * @return the remoteAddress
	 */
	public String getRemoteAddress() {
		return remoteAddress;
	}

	/**
	 * @param lastActiveDate the lastActiveDate to set
	 */
	public void setLastActiveDate(Date lastActiveDate) {
		this.lastActiveDate = lastActiveDate;
	}

	/**
	 * @return the lastActiveDate
	 */
	public Date getLastActiveDate() {
		return lastActiveDate;
	}
}
