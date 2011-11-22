/**
 * 
 */
package com.wordpress.gertonscorner.security.domain;

import java.io.Serializable;

/**
 * @author Gerton
 *
 */
public class RegisteredService implements Serializable {

	private static final long serialVersionUID = -636331732314513680L;
	
	private String id;
	private String serviceAuthentication;
	
	public RegisteredService() {}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param serviceAuthentication the serviceAuthentication to set
	 */
	public void setServiceAuthentication(String serviceAuthentication) {
		this.serviceAuthentication = serviceAuthentication;
	}

	/**
	 * @return the serviceAuthentication
	 */
	public String getServiceAuthentication() {
		return serviceAuthentication;
	}
}
