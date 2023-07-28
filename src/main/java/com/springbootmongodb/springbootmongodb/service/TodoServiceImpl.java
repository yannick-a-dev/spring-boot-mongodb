package com.springbootmongodb.springbootmongodb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootmongodb.springbootmongodb.exception.TodoCollectionException;
import com.springbootmongodb.springbootmongodb.model.TodoDto;
import com.springbootmongodb.springbootmongodb.repo.TodoRepository;

import jakarta.validation.ConstraintViolationException;

@Service
public class TodoServiceImpl implements TodoService {
	
	@Autowired
	private TodoRepository todoRepository;

	@Override
	public void createTodo(TodoDto todo) throws ConstraintViolationException, TodoCollectionException{
	   Optional<TodoDto> todoOptional = todoRepository.findByTodo(todo.getTodo());
	   if(todoOptional.isPresent()) {
		   throw new TodoCollectionException(TodoCollectionException.TodoAllreadyExists());
	   }else {
		   todo.setCreatedAt(new Date(System.currentTimeMillis()));
		   todoRepository.save(todo);
	   }
	}

	@Override
	public List<TodoDto> getAllTodos() {
       List<TodoDto> todos = todoRepository.findAll();
       if(todos.size()>0) {
    	   return todos;
       }else {
   		return new ArrayList<TodoDto>(); 
       }

	}

	@Override
	public TodoDto getSingleTodo(String id) throws TodoCollectionException {
	   Optional<TodoDto> OptionalTodo = todoRepository.findById(id);
	   if(!OptionalTodo.isPresent()) {
		   throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
	   }else {
		   return OptionalTodo.get();
	   }
	}

	@Override
	public void updateTodo(String id, TodoDto todo) throws TodoCollectionException {
	   Optional<TodoDto> todoWithId = todoRepository.findById(id);
	   Optional<TodoDto> todoWithSameName = todoRepository.findByTodo(todo.getTodo());
	   
	   if(todoWithId.isPresent()) {
		   if(todoWithSameName.isPresent() && !todoWithSameName.get().getId().equals(id)) {
			   throw new TodoCollectionException(TodoCollectionException.TodoAllreadyExists());
		   }
	   }
	   if(todoWithId.isPresent()) {
		 TodoDto todoToUpdate = todoWithId.get();
		 todoToUpdate.setTodo(todo.getTodo());
		 todoToUpdate.setDescription(todo.getDescription());
		 todoToUpdate.setCompleted(todo.getCompleted());
		 todoToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
		 todoRepository.save(todoToUpdate);
	   }else {
		   throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
	   }
	}

	@Override
	public void deleteTodoById(String id) throws TodoCollectionException {
	   Optional<TodoDto> todoOptional = todoRepository.findById(id);
	   if(!todoOptional.isPresent()) {
		   throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
	   }else {
		   todoRepository.deleteById(id);
	   }
		
	}

}
