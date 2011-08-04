package com.wordpress.gertonscorner.todo.services;

import java.util.Collection;

import com.wordpress.gertonscorner.todo.dto.TodoDTO;

/**
 * Interface Todo service
 * 
 * @author Gerton
 *
 */
public interface ITodoService {
	
	/**
	 * Get Todo transfer object by given id
	 * 
	 * @param id
	 * @return Todo transfer object
	 */
	TodoDTO getTodoById(String id);
	
	/**
	 * Get all available orders (converted to order transfer objects)
	 * 
	 * @return List of Todo transfer objects
	 */
	Collection<TodoDTO> getTodos();
	
	/**
	 * Insert a new Todo.
	 * 
	 * @param order Todo transfer object
	 * @return Todo transfer object
	 */
	TodoDTO insertTodo(TodoDTO todoDTO);
	
	/**
	 * Update Todo with given data
	 * 
	 * @param todoDTO Todo transfer object containing the updated data
	 * @return Todo transfer object
	 */
	TodoDTO updateTodo(TodoDTO todoDTO);
	
	/**
	 * Delete Todo for given id
	 * 
	 * @param id The id of the Todo
	 */
	void deleteTodo(String id);
	
}
