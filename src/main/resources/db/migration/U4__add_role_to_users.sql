-- U4__add_role_to_users.sql
-- Rollback: remove role column from users table

ALTER TABLE users
    DROP COLUMN role;
