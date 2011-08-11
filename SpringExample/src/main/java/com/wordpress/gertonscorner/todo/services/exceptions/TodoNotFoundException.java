package com.wordpress.gertonscorner.todo.services.exceptions;

public class TodoNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1042618009580375284L;

	public TodoNotFoundException() {
		
	}
	public TodoNotFoundException(String message) {
		super(message);
	}
}
