package com.wordpress.gertonscorner.todo.services.impl;

import java.util.ArrayList;
import java.util.Collection;
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
import com.wordpress.gertonscorner.todo.services.ITodoService;


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
	public TodoDTO getTodoById(String id) {
		return mapper.map(todoDao.selectTodo(id),TodoDTO.class);
	}

	/* (non-Javadoc)
	 * @see com.wordpress.gertonscorner.todo.services.ITodoService#insertOrder(com.wordpress.gertonscorner.todo.dto.TodoDTO)
	 */
	public TodoDTO insertTodo(TodoDTO todoDTO) {
		Collection<ErrorDTO> errors = new ArrayList<ErrorDTO>(0);
		Todo todo = mapper.map(todoDTO, Todo.class);
		
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
		Todo todo = mapper.map(todoDTO, Todo.class);
		
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
			todoDao.updateTodo(mapper.map(todoDTO, Todo.class));
		}
		
		return todoDTO;
	}

}
