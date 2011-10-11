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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wordpress.gertonscorner.security.services.IUserService;
import com.wordpress.gertonscorner.security.services.exceptions.UserNotFoundException;

/**
 * User web controller.
 * 
 * @author Gerton
 *
 */
@Controller
public class UserController {
	@Autowired
	private IUserService userService;
	
	/**
	 * Get username by given id
	 * 
	 * @param id
	 * @return username
	 */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String getUserName(@PathVariable("id") String id) {
		return userService.getUserById(id).getUsername();
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void userNotFound() {}
}
