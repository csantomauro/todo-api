package com.cs.todo_api.controller;

import com.cs.todo_api.dto.TodoRequestDto;
import com.cs.todo_api.dto.TodoResponseDto;
import com.cs.todo_api.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getAllTodos(Authentication authentication){
        return ResponseEntity.ok(todoService.getAllTodos(authentication.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> getTodoById(@PathVariable long id, Authentication authentication){
        return ResponseEntity.ok(todoService.getTodoById(id, authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(
            @Valid @RequestBody TodoRequestDto requestDto,
            Authentication authentication
    ){
        TodoResponseDto created = todoService.createTodo(requestDto, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoRequestDto requestDto,
            Authentication authentication
            ) {
        return ResponseEntity.ok(todoService.updateTodo(id, requestDto, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id, Authentication authentication) {
        todoService.deleteTodo(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
