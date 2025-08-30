-- Rollback for V2__create_transactions.sql

-- Drop transactions first since it may have a foreign key to categories
DROP TABLE IF EXISTS transactions CASCADE;

-- Then drop categories
DROP TABLE IF EXISTS categories CASCADE;
