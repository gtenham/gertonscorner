package com.wordpress.gertonscorner.todo.dao;

import java.util.List;

import com.wordpress.gertonscorner.todo.domain.Todo;

/**
 * Interface TodoDAO
 * 
 * @author Gerton
 *
 */
public interface ITodoDao {
	
	/**
	 * Get all available todos
	 * 
	 * @return List of Todo
	 */
	List<Todo> getTodos();
	
	/**
	 * Get Todo by id
	 * 
	 * @param id
	 * @return Todo
	 */
	Todo selectTodo(String id);
	
	/**
	 * Insert new Todo
	 * 
	 * @param todo
	 */
	void insertTodo(Todo todo);
	
	/**
	 * Delete todo by id
	 * 
	 * @param id
	 */
	void deleteTodo(String id);
	
	/**
	 * Update todo with given todo data
	 * 
	 * @param todo
	 */
	void updateTodo(Todo todo);
	
}
