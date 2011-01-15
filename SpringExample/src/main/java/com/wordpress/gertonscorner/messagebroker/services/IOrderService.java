package com.wordpress.gertonscorner.messagebroker.services;

import java.util.Collection;

import com.wordpress.gertonscorner.messagebroker.dto.OrderDTO;

public interface IOrderService {
	OrderDTO getOrderById(String id);
	Collection<OrderDTO> getOrders();
	
	void insertOrder(OrderDTO order);
	void updateOrder(OrderDTO order);
	void deleteOrder(String id);
	
}
