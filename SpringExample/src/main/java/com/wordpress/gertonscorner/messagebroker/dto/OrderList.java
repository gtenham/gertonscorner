package com.wordpress.gertonscorner.messagebroker.dto;

import java.io.Serializable;
import java.util.Collection;

/**
 * Order list transfer object
 * 
 * @author Gerton
 *
 */
public class OrderList implements Serializable {
	private static final long serialVersionUID = 6025002681153990799L;
	
	private Collection<OrderDTO> orders;
	
	public OrderList() {}
	public OrderList(Collection<OrderDTO> orders) {
		this.orders = orders;
	}
	public void setOrders(Collection<OrderDTO> orders) {
		this.orders = orders;
	}
	public Collection<OrderDTO> getOrders() {
		return orders;
	}
}
