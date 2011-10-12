/**
 * 
 */
package com.wordpress.gertonscorner.security.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wordpress.gertonscorner.security.services.IAuthenticationService;
import com.wordpress.gertonscorner.security.services.IUserService;
import com.wordpress.gertonscorner.security.services.exceptions.UserNotFoundException;

/**
 * Authentication web controller
 * 
 * @author Gerton
 *
 */
@Controller
@RequestMapping("/authentication")
public class AuthenticationController {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IAuthenticationService authenticationService;
	
	/**
	 * Get token for given username
	 * 
	 * @param username
	 * @return encrypted token
	 */
	@RequestMapping(value = "/token", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String getToken(@RequestParam(value="username", required = false ) String username) {
		return authenticationService.getTokenForUser(userService.getUserByName(username));
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void userNotFound() {}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public void internalServerError() {}
}
