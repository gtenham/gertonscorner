package com.wordpress.gertonscorner.messagebroker.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordpress.gertonscorner.messagebroker.dto.OrderDTO;
import com.wordpress.gertonscorner.messagebroker.dto.OrderList;
import com.wordpress.gertonscorner.messagebroker.services.IOrderService;

/**
 * Order web controller
 * 
 * @author Gerton
 *
 */
@Controller
public class OrderController {
	@Autowired
	private IOrderService orderService;
	
	/**
	 * Get all available orders and returns them directly into the response
	 * 
	 * @return OrderList transfer object
	 */
	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public @ResponseBody OrderList orderList() {		
		return new OrderList(orderService.getOrders());
	}
	
	/**
	 * Add order and returns all orders directly into the response.
	 * 
	 * @param order The new order data
	 * @return OrderList transfer object
	 */
	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	public @ResponseBody OrderList addOrder(@RequestBody OrderDTO order) {		
		orderService.insertOrder(order);
		return new OrderList(orderService.getOrders());
	}
	
	/**
	 * Get order by given orderId
	 * 
	 * @param orderId
	 * @return Order transfer object
	 */
	@RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
	public @ResponseBody OrderDTO getOrder(@PathVariable("id") String orderId) {		
		return orderService.getOrderById(orderId);
	}
	
	/**
	 * Update the order for id with given order data and return the updated
	 * order directly into the response.
	 * 
	 * @param orderId the id of the order
	 * @param order The order data to update
	 * @return Order transfer object
	 */
	@RequestMapping(value = "/orders/{id}", method = RequestMethod.PUT)
	public @ResponseBody OrderDTO updateOrder(@PathVariable("id") String orderId,
										  @RequestBody OrderDTO order) {
		orderService.updateOrder(order);
		return orderService.getOrderById(orderId);
	}
	
	/**
	 * Delete order for given orderId and returns all 
	 * orders directly into the response.
	 * 
	 * @param orderId the id of the order
	 * @return OrderList transfer object
	 */
	@RequestMapping(value = "/orders/{id}", method = RequestMethod.DELETE)
	public @ResponseBody OrderList deleteOrder(@PathVariable("id") String orderId) {
		orderService.deleteOrder(orderId);
		return new OrderList(orderService.getOrders());
	}
}
