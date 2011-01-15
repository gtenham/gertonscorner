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

@Controller
public class OrderController {
	@Autowired
	private IOrderService orderService;
	
	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public @ResponseBody OrderList orderList() {		
		return new OrderList(orderService.getOrders());
	}
	
	@RequestMapping(value = "/orders", method = RequestMethod.POST)
	public @ResponseBody OrderList addOrder(@RequestBody OrderDTO order) {		
		orderService.insertOrder(order);
		return new OrderList(orderService.getOrders());
	}
	@RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
	public @ResponseBody OrderDTO getOrder(@PathVariable("id") String orderId) {		
		return orderService.getOrderById(orderId);
	}
	
	@RequestMapping(value = "/orders/{id}", method = RequestMethod.PUT)
	public @ResponseBody OrderDTO updateOrder(@PathVariable("id") String orderId,
										  @RequestBody OrderDTO order) {
		orderService.updateOrder(order);
		return orderService.getOrderById(orderId);
	}
	
	@RequestMapping(value = "/orders/{id}", method = RequestMethod.DELETE)
	public @ResponseBody OrderList deleteOrder(@PathVariable("id") String orderId) {
		orderService.deleteOrder(orderId);
		return new OrderList(orderService.getOrders());
	}
}
