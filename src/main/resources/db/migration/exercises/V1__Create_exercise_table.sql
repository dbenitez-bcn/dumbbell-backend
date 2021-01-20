CREATE TABLE IF NOT EXISTS EXERCISES (
    id          SERIAL PRIMARY KEY,
    name        VARCHAR,
    description VARCHAR,
    difficulty  smallint
)