package com.cs.todo_api.controller;

import com.cs.todo_api.dto.TodoRequestDto;
import com.cs.todo_api.dto.TodoResponseDto;
import com.cs.todo_api.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    private static final String TEMP_USERNAME = "demo-user";

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getAllTodos(){
        return ResponseEntity.ok(todoService.getAllTodos(TEMP_USERNAME));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> getTodoById(@PathVariable long id){
        return ResponseEntity.ok(todoService.getTodoById(id, TEMP_USERNAME));
    }

    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@Valid @RequestBody TodoRequestDto requestDto){
        TodoResponseDto created = todoService.createTodo(requestDto, TEMP_USERNAME);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDto> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoRequestDto requestDto) {
        return ResponseEntity.ok(todoService.updateTodo(id, requestDto, TEMP_USERNAME));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id, TEMP_USERNAME);
        return ResponseEntity.noContent().build();
    }
}
