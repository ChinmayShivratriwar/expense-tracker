-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE budgets (
    id               BIGSERIAL PRIMARY KEY,              -- simple PK (you can switch to UUID if you prefer)
    user_id          UUID        NOT NULL,               -- FK to users(id)
    category         VARCHAR(64) NOT NULL,               -- e.g. "Food", "Transport"
    limit_amount     NUMERIC(14,2) NOT NULL CHECK (limit_amount >= 0),
    spent_amount     NUMERIC(14,2) NOT NULL DEFAULT 0 CHECK (spent_amount >= 0),
    month            SMALLINT    NOT NULL CHECK (month BETWEEN 1 AND 12),
    year             SMALLINT    NOT NULL CHECK (year BETWEEN 2000 AND 2100),
    created_at       TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at       TIMESTAMPTZ NOT NULL DEFAULT now(),

    CONSTRAINT uq_budget_user_cat_month_year UNIQUE (user_id, category, month, year),
    CONSTRAINT fk_budgets_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Helpful index for lookups
CREATE INDEX idx_budgets_user_month_year_cat
    ON budgets(user_id, year, month, category);