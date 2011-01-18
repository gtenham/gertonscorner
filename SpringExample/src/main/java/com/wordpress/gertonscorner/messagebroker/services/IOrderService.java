package com.wordpress.gertonscorner.messagebroker.services;

import java.util.Collection;

import com.wordpress.gertonscorner.messagebroker.dto.OrderDTO;

/**
 * Interface Order service
 * 
 * @author Gerton
 *
 */
public interface IOrderService {
	
	/**
	 * Get Order transfer object by given id
	 * 
	 * @param id
	 * @return Order transfer object
	 */
	OrderDTO getOrderById(String id);
	
	/**
	 * Get all available orders (converted to order transfer objects)
	 * 
	 * @return List of Order transfer objects
	 */
	Collection<OrderDTO> getOrders();
	
	/**
	 * Insert a new Order.
	 * 
	 * @param order Order transfer object
	 */
	void insertOrder(OrderDTO order);
	
	/**
	 * Update Order with given order data
	 * 
	 * @param order Order transfer object containing the updated data
	 */
	void updateOrder(OrderDTO order);
	
	/**
	 * Delete Order for given orderId
	 * 
	 * @param id The id of the Order
	 */
	void deleteOrder(String id);
	
}
