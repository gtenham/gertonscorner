package com.wordpress.gertonscorner.todo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.wordpress.gertonscorner.todo.dto.TodoDTO;
import com.wordpress.gertonscorner.todo.dto.TodoList;
import com.wordpress.gertonscorner.todo.dto.TodoSortDTO;
import com.wordpress.gertonscorner.todo.services.ITodoService;
import com.wordpress.gertonscorner.todo.services.exceptions.TodoNotFoundException;

/**
 * Todo web controller
 * 
 * @author Gerton
 *
 */
@Controller
public class TodoController {
	@Autowired
	private ITodoService todoService;
	
	/**
	 * Get all available todos and returns them directly into the response
	 * 
	 * @return TodoList transfer object
	 */
	@RequestMapping(value = "/todos", method = RequestMethod.GET)
	public @ResponseBody TodoList todoList() {		
		return new TodoList(todoService.getTodos());
	}
	
	/**
	 * Add todo and returns the new todo directly into the response.
	 * 
	 * @param todo The new todo data
	 * @return Todo transfer object
	 */
	@RequestMapping(value = "/todos", method = RequestMethod.POST)
	public @ResponseBody TodoDTO addTodo(@RequestBody TodoDTO todo) {		
		return todoService.insertTodo(todo);
	}
	
	/**
	 * Get todo by given id
	 * 
	 * @param id
	 * @return Todo transfer object
	 */
	@RequestMapping(value = "/todos/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody TodoDTO getTodo(@PathVariable("id") String id) {		
		return todoService.getTodoById(id);
	}
	
	/**
	 * Update the todo for id with given todo data and return the updated
	 * todo directly into the response.
	 * 
	 * @param id the id of the todo
	 * @param todo The todo data to update
	 * @return Todo transfer object
	 */
	@RequestMapping(value = "/todos/{id}", method = RequestMethod.PUT)
	public @ResponseBody TodoDTO updateTodo(@PathVariable("id") String id,
										  @RequestBody TodoDTO todo) {
		return todoService.updateTodo(todo);
	}
	
	/**
	 * Delete todo for given id and returns all 
	 * todos directly into the response.
	 * 
	 * @param id the id of the todo
	 * @return TodoList transfer object
	 */
	@RequestMapping(value = "/todos/{id}", method = RequestMethod.DELETE)
	public @ResponseBody TodoList deleteTodos(@PathVariable("id") String id) {
		todoService.deleteTodo(id);
		return new TodoList(todoService.getTodos());
	}
	
	/**
	 * Add/update sorting for todos.
	 * 
	 * @param TodoSortDTO Contains sorted list of todo ids 
	 * @return http status 204 - NO CONTENT
	 */
	@RequestMapping(value = "/todos/sort", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateTodoSorting(@RequestBody TodoSortDTO sort) {		
		todoService.updateTodoSorting(sort);
	}
	
	@ExceptionHandler(TodoNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void todoNotFound() {}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public void todoBadRequest() {}
	
}
