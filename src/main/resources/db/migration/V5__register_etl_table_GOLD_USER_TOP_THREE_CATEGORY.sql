-- V5__register_etl_table_GOLD_USER_TOP_THREE_CATEGORY.sql
-- Adding foe ETl Purposes

CREATE TABLE gold_user_top_three_category
(
    user_id text NOT NULL,
    category text NOT NULL,
    total_amount double precision,
    txn_count bigint,
    rank double precision,
    CONSTRAINT pk_user_category_new PRIMARY KEY (user_id, category)
);

