package com.chinmayshivratriwar.expense_tracker.controller;

import com.chinmayshivratriwar.expense_tracker.dto.AuthResponse;
import com.chinmayshivratriwar.expense_tracker.dto.LoginRequest;
import com.chinmayshivratriwar.expense_tracker.dto.RegisterRequest;
import com.chinmayshivratriwar.expense_tracker.service.AuthService;
import com.chinmayshivratriwar.expense_tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody RegisterRequest request){
        userService.registerUser(request);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody String refreshToken) {
        AuthResponse response = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout( @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String refreshToken = authHeader.substring(7);
        authService.logout(refreshToken);
        return ResponseEntity.ok().build();
    }
}

