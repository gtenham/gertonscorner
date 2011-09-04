package com.wordpress.gertonscorner.todo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Todo transfer object
 * 
 * @author Gerton
 *
 */
public class TodoDTO implements Serializable {

	private static final long serialVersionUID = -7974075876533955036L;

	private String id;
	private Integer done;
	private Integer order;
	private String description;
	private Date startDate;
	private Collection<ErrorDTO> errors = new ArrayList<ErrorDTO>(0);
	
	public TodoDTO() {}
	public TodoDTO(String id, String description) {
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
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	public void setErrors(Collection<ErrorDTO> errors) {
		this.errors = errors;
	}
	public Collection<ErrorDTO> getErrors() {
		return errors;
	}
}
