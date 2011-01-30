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

	private String errorField;
	private String errorMessage;
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorField(String errorField) {
		this.errorField = errorField;
	}
	public String getErrorField() {
		return errorField;
	}
}
