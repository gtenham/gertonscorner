/**
 * 
 */
package com.wordpress.gertonscorner.security.dao;

import com.wordpress.gertonscorner.security.domain.RegisteredService;

/**
 * @author Gerton
 *
 */
public interface IRegisteredServiceDao {

	/**
	 * Get a registered service by its id
	 * 
	 * @param id
	 * @return
	 */
	RegisteredService getServiceById(String id);
}
