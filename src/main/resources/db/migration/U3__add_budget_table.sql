-- U3__add_budget_table.sql
-- Rollback: drop budgets table and its index

DROP INDEX IF EXISTS idx_budgets_user_month_year_cat;
DROP TABLE IF EXISTS budgets;
