CREATE TABLE IF NOT EXISTS reminder
(
    id           BIGINT PRIMARY KEY,
    "user_id"    BIGINT,
    title        TEXT,
    description  TEXT,
    "place_name" TEXT,
    "cr_date"    DATE,
    latitude     double precision,
    longitude    double precision
);

CREATE TABLE IF NOT EXISTS "user"
(
    id       BIGINT PRIMARY KEY,
    username TEXT,
    password TEXT,
    email    TEXT,
    role     TEXT
);
CREATE SEQUENCE IF NOT EXISTS reminders_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS users_id_seq START WITH 1 INCREMENT BY 1;