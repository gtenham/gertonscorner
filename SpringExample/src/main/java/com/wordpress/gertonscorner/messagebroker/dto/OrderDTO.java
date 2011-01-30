package com.wordpress.gertonscorner.messagebroker.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

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
	private Collection<ErrorDTO> errors = new ArrayList<ErrorDTO>(0);
	
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
	public void setErrors(Collection<ErrorDTO> errors) {
		this.errors = errors;
	}
	public Collection<ErrorDTO> getErrors() {
		return errors;
	}
}
