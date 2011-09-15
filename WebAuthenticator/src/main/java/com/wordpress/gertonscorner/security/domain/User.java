package com.wordpress.gertonscorner.security.domain;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 4095943060729596113L;
	
	private String id;
	private String username;
	private String password;
	private Integer active;
	private String service_url;
	
	public User() {}

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
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Integer active) {
		this.active = active;
	}

	/**
	 * @return the active
	 */
	public Integer getActive() {
		return active;
	}

	/**
	 * @param service_url the service_url to set
	 */
	public void setService_url(String service_url) {
		this.service_url = service_url;
	}

	/**
	 * @return the service_url
	 */
	public String getService_url() {
		return service_url;
	}
}
