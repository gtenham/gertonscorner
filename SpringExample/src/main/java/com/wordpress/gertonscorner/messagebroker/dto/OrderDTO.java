package com.wordpress.gertonscorner.messagebroker.dto;

import java.io.Serializable;

/**
 * Order transfer object
 * 
 * @author Gerton
 *
 */
public class OrderDTO implements Serializable {

	private static final long serialVersionUID = -7974075876533955036L;

	private String id;
	private String description;
	
	public OrderDTO() {}
	public OrderDTO(String id, String description) {
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
