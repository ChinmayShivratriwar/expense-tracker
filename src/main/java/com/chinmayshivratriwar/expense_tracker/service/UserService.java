package com.chinmayshivratriwar.expense_tracker.service;

import com.chinmayshivratriwar.expense_tracker.dto.RegisterRequest;
import com.chinmayshivratriwar.expense_tracker.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    User registerUser(RegisterRequest request);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    List<User> getAllUsers();
    long getTotalUsers();
}
