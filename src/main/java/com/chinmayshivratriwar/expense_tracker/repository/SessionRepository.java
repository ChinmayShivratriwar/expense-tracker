package com.chinmayshivratriwar.expense_tracker.repository;

import com.chinmayshivratriwar.expense_tracker.entities.Session;
import com.chinmayshivratriwar.expense_tracker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {

    Optional<Session> findByRefreshToken(String refreshToken);

    List<Session> findByUser(User user);

    void deleteByUser(User user);

    void deleteByRefreshToken(String refreshToken);
}
