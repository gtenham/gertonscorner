package com.wordpress.gertonscorner.messagebroker.dto;

import java.io.Serializable;

/**
 * Error transfer object
 * 
 * @author Gerton
 *
 */
public class ErrorDTO implements Serializable {

	private static final long serialVersionUID = -2577938088819066878L;

	private String field;
	private String message;
	
	public void setField(String field) {
		this.field = field;
	}
	public String getField() {
		return field;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	
	
}
