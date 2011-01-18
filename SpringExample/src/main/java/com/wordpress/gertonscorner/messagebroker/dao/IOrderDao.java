package com.wordpress.gertonscorner.messagebroker.dao;

import java.util.List;

import com.wordpress.gertonscorner.messagebroker.domain.Order;

/**
 * Interface OrderDAO
 * 
 * @author Gerton
 *
 */
public interface IOrderDao {
	
	/**
	 * Get all available orders
	 * 
	 * @return List of Orders
	 */
	List<Order> getOrders();
	
	/**
	 * Get Order by orderId
	 * 
	 * @param orderId
	 * @return Order
	 */
	Order selectOrder(String orderId);
	
	/**
	 * Insert new Order
	 * 
	 * @param order
	 */
	void insertOrder(Order order);
	
	/**
	 * Delete order by orderId
	 * 
	 * @param orderId
	 */
	void deleteOrder(String orderId);
	
	/**
	 * Update order with given order data
	 * 
	 * @param order
	 */
	void updateOrder(Order order);
	
}
