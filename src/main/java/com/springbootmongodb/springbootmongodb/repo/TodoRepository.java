package com.springbootmongodb.springbootmongodb.repo;


import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.springbootmongodb.springbootmongodb.model.TodoDto;

public interface TodoRepository extends MongoRepository<TodoDto, String> {

   @Query("{'todo': ?0}") //0 pour dire le premier parametre
   Optional<TodoDto> findByTodo(String todo);
   
}
