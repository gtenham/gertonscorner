/**
 * 
 */
package com.wordpress.gertonscorner.security.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wordpress.gertonscorner.security.annotations.Authentication;
import com.wordpress.gertonscorner.security.services.IUserService;

/**
 * User web controller.
 * 
 * @author Gerton
 *
 */
@Controller
public class UserController extends AbstractWasController {
	
	@Autowired
	private IUserService userService;
	
	/**
	 * Get user fullname for given user name
	 * 
	 * @param username
	 * @return fullName
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@Authentication(proxyName="was-proxy")
	public @ResponseBody String getUserFullName(@RequestParam(value="username", required = true ) String username
										 ,HttpServletRequest request) {
		return userService.getUserByName(username).getFullName();
	}
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Authentication(proxyName="was-proxy")
	public void updateUserKey(@PathVariable("id") String id
							 ,@RequestBody String publicKey
							 ,HttpServletRequest request ) {
		userService.updatePublicKey(id, publicKey);
	}
}
