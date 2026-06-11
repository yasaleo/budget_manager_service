package com.github.yasaleo.budgetmanager.budget_manager.auth.service;

import com.github.yasaleo.budgetmanager.budget_manager.auth.dto.RegisterRequest;
import com.github.yasaleo.budgetmanager.budget_manager.auth.enitity.User;
import com.github.yasaleo.budgetmanager.budget_manager.auth.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService  (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> register(RegisterRequest registerRequest){
        if (userRepository.existsByEmail(registerRequest.getEmail())){
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }


        User user = new User();

        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setPassword(registerRequest.getPassword());

        userRepository.save(user);

        return  ResponseEntity.ok("User registered successfully");
    }
}
