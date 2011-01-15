package com.wordpress.gertonscorner.messagebroker.dao;

import java.util.List;

import com.wordpress.gertonscorner.messagebroker.domain.Order;

public interface IOrderDao {
	List<Order> getOrders();
	Order selectOrder(String orderId);
	
	void insertOrder(Order order);
	void deleteOrder(String orderId);
	void updateOrder(Order order);
	
}
