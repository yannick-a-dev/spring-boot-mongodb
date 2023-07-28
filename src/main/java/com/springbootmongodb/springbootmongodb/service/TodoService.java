package com.springbootmongodb.springbootmongodb.service;

import java.util.List;

import com.springbootmongodb.springbootmongodb.exception.TodoCollectionException;
import com.springbootmongodb.springbootmongodb.model.TodoDto;

import jakarta.validation.ConstraintViolationException;

public interface TodoService {

	public void createTodo(TodoDto todo)throws ConstraintViolationException, TodoCollectionException;
	
	   public List<TodoDto> getAllTodos();
	   
	   public TodoDto getSingleTodo(String id) throws TodoCollectionException;
	   
	   public void updateTodo(String id, TodoDto todo)throws TodoCollectionException;
	   
	   public void deleteTodoById(String id) throws TodoCollectionException;

}
