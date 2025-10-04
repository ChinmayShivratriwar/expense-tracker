package com.chinmayshivratriwar.expense_tracker.entities;

import com.chinmayshivratriwar.expense_tracker.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import org.hibernate.annotations.GenericGenerator;
import java.util.UUID;

@Entity
@Getter
@Setter
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "password_hash", nullable = false, columnDefinition = "TEXT")
    private String passwordHash;

    @Column(columnDefinition = "TEXT")
    private String salt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;

    @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false)
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;
}