package com.cs.todo_api.repository;

import com.cs.todo_api.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByUsername(String username);

    List<Todo> findByUsernameAndCompleted(String username, boolean completed);
}
