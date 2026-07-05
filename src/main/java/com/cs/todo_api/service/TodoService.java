package com.cs.todo_api.service;

import com.cs.todo_api.dto.TodoRequestDto;
import com.cs.todo_api.dto.TodoResponseDto;
import com.cs.todo_api.exception.TodoNotFoundException;
import com.cs.todo_api.model.Todo;
import com.cs.todo_api.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public List<TodoResponseDto> getAllTodos(String username){
        return todoRepository.findByUsername(username)
                .stream()
                .map(this::toResponseDto)
                .toList();
    }

    public TodoResponseDto getTodoById(Long id, String username){
        Todo todo = findTodoOrThrow(id, username);
        return toResponseDto(todo);
    }

    public TodoResponseDto createTodo(TodoRequestDto requestDto, String username){
        Todo todo = new Todo();
        todo.setTitle(requestDto.getTitle());
        todo.setDescription(requestDto.getDescription());
        todo.setCompleted(requestDto.isCompleted());
        todo.setUsername(username);

        Todo saved = todoRepository.save(todo);
        return toResponseDto(saved);
    }

    public TodoResponseDto updateTodo(Long id, TodoRequestDto requestDto, String username) {
        Todo todo = findTodoOrThrow(id, username);

        todo.setTitle(requestDto.getTitle());
        todo.setDescription(requestDto.getDescription());
        todo.setCompleted(requestDto.isCompleted());

        Todo updated = todoRepository.save(todo);
        return toResponseDto(updated);
    }

    public void deleteTodo(Long id, String username) {
        Todo todo = findTodoOrThrow(id, username);
        todoRepository.delete(todo);
    }

    private Todo findTodoOrThrow(Long id, String username) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(
                        () -> new TodoNotFoundException("Todo not found with id: " + id)
                );

        if (!todo.getUsername().equals(username)) {
            throw new TodoNotFoundException("Todo not found with id: " + id);
        }

        return todo;
    }

    private TodoResponseDto toResponseDto(Todo todo){
        return new TodoResponseDto(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.isCompleted(),
                todo.getCreatedAt()
        );
    }
}
