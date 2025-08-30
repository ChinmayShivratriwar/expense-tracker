package com.chinmayshivratriwar.expense_tracker.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String username;
    private String password;
    private String fullName;   // goes into profile
    private String phoneNumber;
    private String address;
}
