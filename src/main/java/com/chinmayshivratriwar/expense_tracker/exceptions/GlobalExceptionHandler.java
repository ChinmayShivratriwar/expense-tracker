package com.chinmayshivratriwar.expense_tracker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AuthException.class)
    public ResponseEntity<?> handleAuthException(AuthException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", Instant.now().toString());
        errorBody.put("status", HttpStatus.FORBIDDEN.value());
        errorBody.put("error", "Authentication Error");
        errorBody.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorBody);
    }

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", Instant.now().toString());
        errorBody.put("status", HttpStatus.BAD_REQUEST.value());
        errorBody.put("error", "Validation Failed");
        errorBody.put("message", ex.getBindingResult().getFieldError().getDefaultMessage());

        return ResponseEntity.badRequest().body(errorBody);
    }

    // Handle bad credentials (login)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentials(BadCredentialsException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", Instant.now().toString());
        errorBody.put("status", HttpStatus.UNAUTHORIZED.value());
        errorBody.put("error", "Unauthorized");
        errorBody.put("message", "Invalid username or password");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorBody);
    }

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", Instant.now().toString());
        errorBody.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorBody.put("error", "Internal Server Error");
        errorBody.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
    }
}
