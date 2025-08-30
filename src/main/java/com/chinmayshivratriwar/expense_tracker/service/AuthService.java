package com.chinmayshivratriwar.expense_tracker.service;

import com.chinmayshivratriwar.expense_tracker.dto.AuthResponse;
import com.chinmayshivratriwar.expense_tracker.dto.LoginRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse refreshToken(String refreshToken);
    void logout(String refreshToken);
}

