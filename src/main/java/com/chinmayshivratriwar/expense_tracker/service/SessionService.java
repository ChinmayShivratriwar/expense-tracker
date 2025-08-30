package com.chinmayshivratriwar.expense_tracker.service;

import com.chinmayshivratriwar.expense_tracker.entities.Session;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface SessionService {
    Session createSession(String userId, String refreshToken, String deviceInfo, String ipAddress, long expirySeconds);
    Optional<Session> findByRefreshToken(String refreshToken);
    void deleteSession(String refreshToken);
    void deleteAllSessionsForUser(String userId);
}

