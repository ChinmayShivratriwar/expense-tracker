package com.chinmayshivratriwar.expense_tracker.service.impl;

import com.chinmayshivratriwar.expense_tracker.entities.Session;
import com.chinmayshivratriwar.expense_tracker.repository.SessionRepository;
import com.chinmayshivratriwar.expense_tracker.service.SessionService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    @Override
    public Session createSession(String userId, String refreshToken, String deviceInfo, String ipAddress, long expirySeconds) {
        return null;
    }

    @Override
    public Optional<Session> findByRefreshToken(String refreshToken) {
        return sessionRepository.findByRefreshToken(refreshToken);
    }

    @Override
    public void deleteSession(String refreshToken) {

    }

    @Override
    public void deleteAllSessionsForUser(String userId) {

    }
}
