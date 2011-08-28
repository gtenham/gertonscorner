package com.wordpress.gertonscorner.todo.domain;

import java.io.Serializable;

/**
 * Todo domain entity
 * 
 * @author Gerton
 *
 */
public class Todo implements Serializable {

	private static final long serialVersionUID = -632626680286074037L;
	
	private String id;
	private Integer done;
	private Integer order;
	private String description;
	
	public Todo() {}
	public Todo(String id, String description) {
		this.id = id;
		this.description = description;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setDone(Integer done) {
		this.done = done;
	}
	public Integer getDone() {
		return done;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}
	/**
	 * @return the order
	 */
	public Integer getOrder() {
		return order;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
