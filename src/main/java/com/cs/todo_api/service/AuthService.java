package com.cs.todo_api.service;

import com.cs.todo_api.dto.AuthResponseDto;
import com.cs.todo_api.dto.LoginRequestDto;
import com.cs.todo_api.dto.RegisterRequestDto;
import com.cs.todo_api.exception.UserAlreadyExistsException;
import com.cs.todo_api.model.User;
import com.cs.todo_api.repository.UserRepository;
import com.cs.todo_api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDto register(RegisterRequestDto request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("Username already taken: " + request.getUsername());
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User saved = userRepository.save(user);
        String token = jwtService.generateToken(saved);

        return new AuthResponseDto(token);
    }

    public AuthResponseDto login(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalStateException("User should exist after successful authentication"));

        String token = jwtService.generateToken(user);
        return new AuthResponseDto(token);
    }
}
