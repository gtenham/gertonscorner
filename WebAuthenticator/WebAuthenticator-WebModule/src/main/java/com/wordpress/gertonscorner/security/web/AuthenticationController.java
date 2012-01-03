/**
 * 
 */
package com.wordpress.gertonscorner.security.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wordpress.gertonscorner.security.annotations.Authentication;
import com.wordpress.gertonscorner.security.services.IAuthenticationService;
import com.wordpress.gertonscorner.security.services.IUserService;

/**
 * Authentication web controller
 * 
 * @author Gerton
 * @param <HttpServletRequest>
 *
 */
@Controller
@RequestMapping("/authentication")
public class AuthenticationController extends AbstractWasController{

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IAuthenticationService authenticationService;
	
	private String remoteAddress;
	
	@InitBinder
	public void initBinder(HttpServletRequest request) {
		this.remoteAddress = request.getRemoteAddr();
	}
	
	/**
	 * Get token for given username
	 * 
	 * @param username
	 * @return encrypted token
	 */
	@RequestMapping(value = "/token", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String getToken(@RequestParam(value="username", required = true ) String username) {
		return authenticationService.getTokenForUser(userService.getUserByName(username));
	}
	
	/**
	 * Obtain a service ticket.
	 * 
	 * @param username
	 * @param servicename
	 * @return (encrypted) service ticket
	 */
	@RequestMapping(value = "/serviceticket", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String getServiceTicket( @RequestParam(value="username", required = true ) String username
												 , @RequestParam(value="servicename", required = true ) String servicename) {
		return authenticationService.getServiceTicket( userService.getUserByName(username)
													, servicename
													, this.remoteAddress
													);
	}
	
	/**
	 * Remove the user session for given username
	 * 
	 * @param username
	 */
	@RequestMapping(value = "/serviceticket", method = RequestMethod.DELETE)
	@Authentication(proxyName="was-proxy")
	public void removeSession(@RequestParam(value="username", required = true ) String username
							 ,HttpServletRequest request) {
		authenticationService.invalidateSessionForUser(userService.getUserByName(username));
	}
}
