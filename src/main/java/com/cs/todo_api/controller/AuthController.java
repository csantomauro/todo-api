package com.cs.todo_api.controller;

import com.cs.todo_api.dto.AuthResponseDto;
import com.cs.todo_api.dto.ErrorResponseDto;
import com.cs.todo_api.dto.LoginRequestDto;
import com.cs.todo_api.dto.RegisterRequestDto;
import com.cs.todo_api.exception.UserAlreadyExistsException;
import com.cs.todo_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {
        AuthResponseDto response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        AuthResponseDto response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler({BadCredentialsException.class, UserAlreadyExistsException.class})
    public ResponseEntity<ErrorResponseDto> handleAuthErrors(RuntimeException ex) {
        HttpStatus status = ex instanceof BadCredentialsException
                ? HttpStatus.UNAUTHORIZED
                : HttpStatus.CONFLICT;

        ErrorResponseDto error = new ErrorResponseDto(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                List.of(ex.getMessage())
        );
        return ResponseEntity.status(status).body(error);
    }
}
