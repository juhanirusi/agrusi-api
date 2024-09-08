-- Create the needed tables to the database

----------------
-- Role Table --
----------------

CREATE TABLE IF NOT EXISTS role (
    id BIGINT PRIMARY KEY,
    authority VARCHAR(255) NOT NULL
);

-------------------
-- Account Table --
-------------------

CREATE TABLE IF NOT EXISTS account (
    id BIGINT NOT NULL PRIMARY KEY,
    public_id UUID NOT NULL UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_verified BOOLEAN NOT NULL,
    date_created TIMESTAMP NOT NULL,
    last_updated TIMESTAMP NOT NULL
);

----------------
-- Farm Table --
----------------

CREATE TABLE IF NOT EXISTS farm (
    id BIGINT NOT NULL PRIMARY KEY,
    public_id UUID NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    date_created TIMESTAMP NOT NULL,
    last_updated TIMESTAMP NOT NULL
);

-----------------
-- Field Table --
-----------------

CREATE TABLE IF NOT EXISTS field (
    id BIGINT NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    area geometry(Polygon, 4326) NOT NULL,
    center geometry(Point, 4326) NOT NULL,
    size NUMERIC,
    fk_farm_id BIGINT,

    CONSTRAINT fk_farm FOREIGN KEY (fk_farm_id) REFERENCES farm(id)
);
