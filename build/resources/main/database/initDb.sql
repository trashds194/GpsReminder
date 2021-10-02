CREATE TABLE IF NOT EXISTS location
(
    id        BIGINT PRIMARY KEY,
    "user_id" INTEGER,
    "cr_date" DATE,
    latitude  double precision,
    longitude double precision
);
CREATE SEQUENCE IF NOT EXISTS locations_id_seq START WITH 3 INCREMENT BY 1