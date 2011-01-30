package com.wordpress.gertonscorner.messagebroker.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;
import com.wordpress.gertonscorner.messagebroker.dao.IOrderDao;
import com.wordpress.gertonscorner.messagebroker.domain.Order;
import com.wordpress.gertonscorner.messagebroker.dto.ErrorDTO;
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
	
	@Autowired
	private Validator validator;
	
	public OrderService() {
	}
	
	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.messagebroker.services.IOrderService#getOrders()
	 */
	@RemotingInclude
	public Collection<OrderDTO> getOrders() {
		Collection<OrderDTO> orders = new ArrayList<OrderDTO>(0);
		
		ErrorDTO errordto = new ErrorDTO();
		errordto.setErrorField("SST-01000");
		errordto.setErrorMessage("test message");
		
		for (Order order : orderDao.getOrders()) {
			OrderDTO orderdto = mapper.map(order,OrderDTO.class);
			orderdto.getErrors().add(errordto);
			orders.add(orderdto);
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
	public OrderDTO insertOrder(OrderDTO orderDTO) {
		Collection<ErrorDTO> errors = new ArrayList<ErrorDTO>(0);
		Order order = mapper.map(orderDTO, Order.class);
		
		Collection<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
		if (constraintViolations.size() > 0) {
			for (ConstraintViolation<Order> error : constraintViolations) {
				ErrorDTO errordto = new ErrorDTO();
				errordto.setErrorField(error.getPropertyPath().toString());
				errordto.setErrorMessage(error.getMessage());
				errors.add(errordto);
			}
			orderDTO.setErrors(errors);
		} else {
			orderDao.insertOrder(order);
		}
		
		return orderDTO;
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
	public OrderDTO updateOrder(OrderDTO orderDTO) {
		Collection<ErrorDTO> errors = new ArrayList<ErrorDTO>(0);
		Order order = mapper.map(orderDTO, Order.class);
		
		Collection<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
		if (constraintViolations.size() > 0) {
			for (ConstraintViolation<Order> error : constraintViolations) {
				ErrorDTO errordto = new ErrorDTO();
				errordto.setErrorField(error.getPropertyPath().toString());
				errordto.setErrorMessage(error.getMessage());
				errors.add(errordto);
			}
			orderDTO.setErrors(errors);
		} else {
			orderDao.updateOrder(mapper.map(orderDTO, Order.class));
		}
		
		return orderDTO;
	}

}
