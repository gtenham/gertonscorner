package com.wordpress.gertonscorner.security.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wordpress.gertonscorner.security.services.exceptions.EncryptionException;
import com.wordpress.gertonscorner.security.services.exceptions.ProxyAuthenticationRequiredException;
import com.wordpress.gertonscorner.security.services.exceptions.UserNotFoundException;

/**
 * Web Authentication Service abstract controller
 * (contains general exception handlers)
 * 
 * @author Gerton
 * @param <HttpServletRequest>
 *
 */
public abstract class AbstractWasController {

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void userNotFound() {}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public void internalServerError() {}
	
	@ExceptionHandler(EncryptionException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public void forbiddenRequest() {}
	
	@ExceptionHandler(ProxyAuthenticationRequiredException.class)
	@ResponseStatus(HttpStatus.PROXY_AUTHENTICATION_REQUIRED)
	public void authenticationRequired() {}
}
