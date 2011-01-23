package com.wordpress.gertonscorner.messagebroker.services.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;

import com.wordpress.gertonscorner.messagebroker.dao.IOrderDao;
import com.wordpress.gertonscorner.messagebroker.domain.Order;
import com.wordpress.gertonscorner.messagebroker.dto.OrderDTO;
import com.wordpress.gertonscorner.messagebroker.services.IOrderService;


/**
 * Order service implementation
 * 
 * @author Gerton
 *
 */
@Service("orderService")
@RemotingDestination(channels={"my-amf"})
public class OrderService implements IOrderService {
	@Autowired
	private IOrderDao orderDao;
	
	@Autowired
	private Mapper mapper;
	
	public OrderService() {
	}
	
	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.messagebroker.services.IOrderService#getOrders()
	 */
	@RemotingInclude
	public Collection<OrderDTO> getOrders() {
		Collection<OrderDTO> orders = new ArrayList<OrderDTO>(0);
		for (Order order : orderDao.getOrders()) {
			orders.add(mapper.map(order,OrderDTO.class));
		}
		return orders;
	}

	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.messagebroker.services.IOrderService#getOrderById(java.lang.String)
	 */
	public OrderDTO getOrderById(String id) {
		return mapper.map(orderDao.selectOrder(id),OrderDTO.class);
	}

	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.messagebroker.services.IOrderService#insertOrder(com.wordpress.gertonscorner.messagebroker.dto.OrderDTO)
	 */
	public void insertOrder(OrderDTO order) {
		orderDao.insertOrder(mapper.map(order, Order.class));
	}

	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.messagebroker.services.IOrderService#deleteOrder(java.lang.String)
	 */
	public void deleteOrder(String id) {
		orderDao.deleteOrder(id);
	}

	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.messagebroker.services.IOrderService#updateOrder(com.wordpress.gertonscorner.messagebroker.dto.OrderDTO)
	 */
	public void updateOrder(OrderDTO order) {
		orderDao.updateOrder(mapper.map(order, Order.class));
	}

}
