package com.chinmayshivratriwar.expense_tracker.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private long expiresIn; // in ms seconds
}