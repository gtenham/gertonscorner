/**
 * 
 */
package com.wordpress.gertonscorner.security.web;

import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wordpress.gertonscorner.security.crypto.AES;
import com.wordpress.gertonscorner.security.crypto.SHA;
import com.wordpress.gertonscorner.security.crypto.TEA;
import com.wordpress.gertonscorner.security.services.IUserService;
import com.wordpress.gertonscorner.security.services.exceptions.UserNotFoundException;

/**
 * @author Gerton
 *
 */
@Controller
public class UserController {
	@Autowired
	private IUserService userService;
	
	/**
	 * Get todo by given id
	 * 
	 * @param id
	 * @return Todo transfer object
	 */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String getUserName(@PathVariable("id") String id) {
		return userService.getUserById(id).getUsername();
	}
	
	@RequestMapping(value = "/users/{id}/test", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody String testEncryption(@PathVariable("id") String id) {
		String hashedMessage = null;
		String quote = "søme highly secret text to be encrypteđ";
		try {
			hashedMessage = SHA.getSHA256Digest(userService.getUserById(id).getPassword());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		String encryptVal = AES.encrypt256(hashedMessage, quote);
		String decryptVal = AES.decrypt256(hashedMessage, encryptVal); 
		
		System.out.println("Hashed message: "+hashedMessage);
		System.out.println("AES encrypt: "+encryptVal);
		System.out.println("AES decrypt: "+decryptVal);
		 
		return encryptVal;
	}
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void userNotFound() {}
}
