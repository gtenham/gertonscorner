/**
 * 
 */
package com.wordpress.gertonscorner.security.web;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wordpress.gertonscorner.security.crypto.AESCtr;
import com.wordpress.gertonscorner.security.crypto.SHA;
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
	
	/**
	 * Get token for given userid
	 * 
	 * @param userid
	 * @return encrypted token
	 */
	@RequestMapping(value = "/token/{userid}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String getToken(@PathVariable("userid") String userid) {
		String hashedMessage = null;
		String token = "søme highly secret text to be encrypteđ";
		
		try {
			hashedMessage = SHA.getSHA256Digest(userService.getUserById(userid).getPassword());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		String encryptVal = null;
		
		try {
			encryptVal = AESCtr.encrypt128(hashedMessage, token);
			String decryptVal = AESCtr.decrypt128(hashedMessage, encryptVal); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 
		return encryptVal;
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void userNotFound() {}
}
