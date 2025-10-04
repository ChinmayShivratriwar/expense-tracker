package com.chinmayshivratriwar.expense_tracker.repository;

import com.chinmayshivratriwar.expense_tracker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmailOrUsername(String username, String Email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    List<User> findAll();

    long count();

}