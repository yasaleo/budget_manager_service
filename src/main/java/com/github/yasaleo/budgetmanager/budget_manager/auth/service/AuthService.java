package com.github.yasaleo.budgetmanager.budget_manager.auth.service;

import com.github.yasaleo.budgetmanager.budget_manager.auth.config.SecurityConfig;
import com.github.yasaleo.budgetmanager.budget_manager.auth.dto.LoginRequest;
import com.github.yasaleo.budgetmanager.budget_manager.auth.dto.RegisterRequest;
import com.github.yasaleo.budgetmanager.budget_manager.auth.enitity.User;
import com.github.yasaleo.budgetmanager.budget_manager.auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;

    public AuthService  (UserRepository userRepository , SecurityConfig securityConfig){
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
    }

    public ResponseEntity<String> register(RegisterRequest registerRequest){
        if (userRepository.existsByEmail(registerRequest.getEmail())){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }


        User user = new User();

        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        final String encodedPassWord = securityConfig.passwordEncoder().encode(registerRequest.getPassword());
        user.setPassword(encodedPassWord);

        userRepository.save(user);

        return  ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<String> login(LoginRequest loginRequest) {
        if (!userRepository.existsByEmail(loginRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email not registered");
        }

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();

        final boolean passwordMatches = securityConfig.passwordEncoder().matches(loginRequest.getPassword(), user.getPassword());

        if (passwordMatches) {
            return ResponseEntity.ok("Logged in successfully");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Password or email is incorrect");

    }
}
