-- V4__add_role_to_users.sql
-- Add role column to users table for role-based access (USER / ADMIN)

ALTER TABLE users
    ADD COLUMN role VARCHAR(20) NOT NULL DEFAULT 'USER';
