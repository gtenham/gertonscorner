package com.wordpress.gertonscorner.messagebroker.domain;

import java.io.Serializable;

/**
 * Order domain entity
 * 
 * @author Gerton
 *
 */
public class Order implements Serializable {

	private static final long serialVersionUID = -632626680286074037L;
	
	private String id;
	private String description;
	
	public Order() {}
	public Order(String id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
