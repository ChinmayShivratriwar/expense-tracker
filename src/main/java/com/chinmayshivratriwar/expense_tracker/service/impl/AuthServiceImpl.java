package com.chinmayshivratriwar.expense_tracker.service.impl;

import com.chinmayshivratriwar.expense_tracker.dto.AuthResponse;
import com.chinmayshivratriwar.expense_tracker.dto.LoginRequest;
import com.chinmayshivratriwar.expense_tracker.entities.User;
import com.chinmayshivratriwar.expense_tracker.entities.Session;
import com.chinmayshivratriwar.expense_tracker.exceptions.AuthException;
import com.chinmayshivratriwar.expense_tracker.repository.UserRepository;
import com.chinmayshivratriwar.expense_tracker.repository.SessionRepository;
import com.chinmayshivratriwar.expense_tracker.security.JwtUtil;
import com.chinmayshivratriwar.expense_tracker.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        // 1. Validate the refresh token
        if (!jwtUtil.validateToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        // 2. Extract user details from the refresh token
        String email = jwtUtil.extractUsername(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 3. Check if refresh token is active in DB (session table)
        Session session = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Session not found or already logged out"));

        // 4. Optionally check if refresh token is expired
        if (session.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        // 5. Generate new access token (short-lived)
        String newAccessToken = jwtUtil.generateAccessToken(user);

        // 6. (Optional) Generate a new refresh token too, or reuse the old one
        String newRefreshToken = jwtUtil.generateRefreshToken(user);

        // 7. Update session with new refresh token
        session.setRefreshToken(newRefreshToken);
        session.setExpiresAt(LocalDateTime.now().plusDays(7));
        sessionRepository.save(session);

        return AuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .expiresIn(jwtUtil.getAccessTokenExpiration())
                .build();
    }

    @Override
    public void logout(String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new AuthException("Refresh token required for logout");
        }

        // Delete or mark the session invalid
        sessionRepository.findByRefreshToken(refreshToken)
                .ifPresentOrElse(
                        sessionRepository::delete,
                        () -> { throw new AuthException("Invalid or already logged-out session"); }
                );
        log.info("Logout Successfull");
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmailOrUsername(request.getEmailOrUsername(), request.getEmailOrUsername())
                .orElseThrow(() -> new AuthException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) { // TODO, we are matching password hash so from frontend we need to make sure we send hash in requests
            throw new AuthException("Invalid credentials");
        }

        // enrich claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId().toString());
        claims.put("email", user.getEmail());
        claims.put("username", user.getUsername());
        claims.put("isVerified", user.getIsVerified());

        // generate tokens
        String accessToken = jwtUtil.generateAccessToken(claims, user.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), claims);
        Long refreshTokenExpiry = jwtUtil.getRefreshTokenExpiration(); // in ms

        // persist session
        Session session = new Session();
        session.setUser(user);
        session.setRefreshToken(refreshToken);
        session.setDeviceInfo(request.getDeviceInfo());
        session.setIpAddress(request.getIpAddress());
        session.setExpiresAt(
                LocalDateTime.ofInstant(
                        Instant.now().plusMillis(refreshTokenExpiry),
                        ZoneId.systemDefault()   // or ZoneOffset.UTC
                )
        );
        sessionRepository.save(session);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(refreshTokenExpiry)
                .build();
    }
}

