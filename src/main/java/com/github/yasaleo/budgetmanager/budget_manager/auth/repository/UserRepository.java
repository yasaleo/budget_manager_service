package com.github.yasaleo.budgetmanager.budget_manager.auth.repository;

import com.github.yasaleo.budgetmanager.budget_manager.auth.enitity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
