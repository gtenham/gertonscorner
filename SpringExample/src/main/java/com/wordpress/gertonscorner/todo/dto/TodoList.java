package com.wordpress.gertonscorner.todo.dto;

import java.io.Serializable;
import java.util.Collection;

/**
 * Todo list transfer object
 * 
 * @author Gerton
 *
 */
public class TodoList implements Serializable {
	private static final long serialVersionUID = 6025002681153990799L;
	
	private Collection<TodoDTO> todos;
	
	public TodoList() {}
	public TodoList(Collection<TodoDTO> todos) {
		this.todos = todos;
	}
	public void setTodos(Collection<TodoDTO> todos) {
		this.todos = todos;
	}
	public Collection<TodoDTO> getTodos() {
		return todos;
	}
}
