package com.github.yasaleo.budgetmanager.budget_manager.auth.dto;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String email;

    private String password;
}
