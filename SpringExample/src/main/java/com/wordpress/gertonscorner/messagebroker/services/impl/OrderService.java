package com.wordpress.gertonscorner.messagebroker.services.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wordpress.gertonscorner.messagebroker.dao.IOrderDao;
import com.wordpress.gertonscorner.messagebroker.domain.Order;
import com.wordpress.gertonscorner.messagebroker.dto.OrderDTO;
import com.wordpress.gertonscorner.messagebroker.services.IOrderService;


@Service("orderService")
public class OrderService implements IOrderService {
	@Autowired
	private IOrderDao orderDao;
	
	@Autowired
	private Mapper mapper;
	
	public OrderService() {
	}
	
	public Collection<OrderDTO> getOrders() {
		Collection<OrderDTO> orders = new ArrayList<OrderDTO>(0);
		for (Order order : orderDao.getOrders()) {
			orders.add(mapper.map(order,OrderDTO.class));
		}
		return orders;
	}

	public OrderDTO getOrderById(String id) {
		return mapper.map(orderDao.selectOrder(id),OrderDTO.class);
	}

	public void insertOrder(OrderDTO order) {
		orderDao.insertOrder(mapper.map(order, Order.class));
	}

	public void deleteOrder(String id) {
		orderDao.deleteOrder(id);
	}

	public void updateOrder(OrderDTO order) {
		orderDao.updateOrder(mapper.map(order, Order.class));
	}

}
