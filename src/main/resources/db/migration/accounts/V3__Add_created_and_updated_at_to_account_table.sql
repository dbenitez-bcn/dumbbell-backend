ALTER TABLE ACCOUNTS
    ADD COLUMN created_at timestamp DEFAULT CURRENT_DATE,
    ADD COLUMN updated_at timestamp DEFAULT CURRENT_DATE;