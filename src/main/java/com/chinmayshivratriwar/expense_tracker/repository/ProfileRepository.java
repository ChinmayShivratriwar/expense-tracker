package com.chinmayshivratriwar.expense_tracker.repository;

import com.chinmayshivratriwar.expense_tracker.entities.Profile;
import com.chinmayshivratriwar.expense_tracker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    Optional<Profile> findByUser(User user);

    Optional<Profile> findByUserId(UUID userId);
}