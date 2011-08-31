package com.wordpress.gertonscorner.todo.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.flex.remoting.RemotingDestination;
import org.springframework.flex.remoting.RemotingInclude;
import org.springframework.stereotype.Service;

import com.wordpress.gertonscorner.todo.dao.ITodoDao;
import com.wordpress.gertonscorner.todo.domain.Todo;
import com.wordpress.gertonscorner.todo.dto.ErrorDTO;
import com.wordpress.gertonscorner.todo.dto.TodoDTO;
import com.wordpress.gertonscorner.todo.dto.TodoSortDTO;
import com.wordpress.gertonscorner.todo.services.ITodoService;
import com.wordpress.gertonscorner.todo.services.exceptions.TodoNotFoundException;


/**
 * Todo service implementation
 * 
 * @author Gerton
 *
 */
@Service("todoService")
@RemotingDestination(channels={"my-amf"})
public class TodoService implements ITodoService {
	@Autowired
	private ITodoDao todoDao;
	
	@Autowired
	private Mapper mapper;
	
	@Autowired
	private Validator validator;
	
	public TodoService() {
	}
	
	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.todo.services.ITodoService#getOrders()
	 */
	@RemotingInclude
	public Collection<TodoDTO> getTodos() {
		Collection<TodoDTO> orders = new ArrayList<TodoDTO>(0);
		
		for (Todo todo : todoDao.getTodos()) {
			TodoDTO tododto = mapper.map(todo,TodoDTO.class);
			orders.add(tododto);
		}
		return orders;
	}

	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.todo.services.ITodoService#getOrderById(java.lang.String)
	 */
	public TodoDTO getTodoById(String id) throws TodoNotFoundException {
		Todo todo = todoDao.selectTodo(id);
		if (todo == null) {
			throw new TodoNotFoundException("Todo with ID " + id + " could not be found.");
		}
		return mapper.map(todo,TodoDTO.class);
	}

	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.todo.services.ITodoService#insertOrder(com.wordpress.gertonscorner.todo.dto.TodoDTO)
	 */
	public TodoDTO insertTodo(TodoDTO todoDTO) {
		Collection<ErrorDTO> errors = new ArrayList<ErrorDTO>(0);
		todoDTO.setId(UUID.randomUUID().toString());
		todoDTO.setErrors(errors);
		
		Todo todo = mapper.map(todoDTO, Todo.class);
		
		Collection<ConstraintViolation<Todo>> constraintViolations = validator.validate(todo);
		if (constraintViolations.size() > 0) {
			for (ConstraintViolation<Todo> error : constraintViolations) {
				ErrorDTO errordto = new ErrorDTO();
				errordto.setField(error.getPropertyPath().toString());
				errordto.setMessage(error.getMessage());
				errors.add(errordto);
			}
			todoDTO.setId(null);
			todoDTO.setErrors(errors);
		} else {
			todoDao.insertTodo(todo);
		}
		
		return todoDTO;
	}

	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.todo.services.ITodoService#deleteOrder(java.lang.String)
	 */
	public void deleteTodo(String id) {
		todoDao.deleteTodo(id);
	}

	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.todo.services.ITodoService#updateOrder(com.wordpress.gertonscorner.todo.dto.TodoDTO)
	 */
	public TodoDTO updateTodo(TodoDTO todoDTO) {
		Collection<ErrorDTO> errors = new ArrayList<ErrorDTO>(0);
		todoDTO.setErrors(errors);
		
		Todo todo = mapper.map(todoDTO, Todo.class);
		
		if (!todoExists(todo.getId())) {
			throw new TodoNotFoundException("Todo with ID " + todo.getId() + " could not be found.");
		}
		
		Collection<ConstraintViolation<Todo>> constraintViolations = validator.validate(todo);
		if (constraintViolations.size() > 0) {
			for (ConstraintViolation<Todo> error : constraintViolations) {
				ErrorDTO errordto = new ErrorDTO();
				errordto.setField(error.getPropertyPath().toString());
				errordto.setMessage(error.getMessage());
				errors.add(errordto);
			}
			todoDTO.setErrors(errors);
		} else {
			// Update Todo with new values
			todoDao.updateTodo(todo);
		}
		
		return todoDTO;
	}

	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.todo.services.ITodoService#updateTodoSorting(com.wordpress.gertonscorner.todo.dto.TodoSortDTO)
	 */
	public void updateTodoSorting(TodoSortDTO sort) {
		List<String> list = (List<String>) sort.getSortedIds();
		
		for (int i = 0; i < list.size(); i++) {
			Todo todo = todoDao.selectTodo(list.get(i));
			if (todo == null) {
				throw new TodoNotFoundException("Todo with ID " + list.get(i) + " could not be found.");
			}
			todo.setOrder(i);
			todoDao.updateTodo(todo);
		}
	}
	
	/**
	 * Check if todo exists
	 * 
	 * @param id
	 * @return
	 */
	private boolean todoExists(String id) {
		return todoDao.selectTodo(id) != null;
	}

}
