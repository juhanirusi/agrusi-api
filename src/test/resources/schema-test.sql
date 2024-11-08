-- Create the needed tables to the database

---------------
-- Sequences --
---------------

DROP SEQUENCE IF EXISTS account_sequence;
DROP SEQUENCE IF EXISTS account_preferences_sequence;
DROP SEQUENCE IF EXISTS user_profile_sequence;
DROP SEQUENCE IF EXISTS role_sequence;
DROP SEQUENCE IF EXISTS address_sequence;
DROP SEQUENCE IF EXISTS farm_sequence;
DROP SEQUENCE IF EXISTS field_sequence;
DROP SEQUENCE IF EXISTS field_usage_sequence;
DROP SEQUENCE IF EXISTS crop_sequence;
DROP SEQUENCE IF EXISTS field_task_sequence;
DROP SEQUENCE IF EXISTS field_resource_application_sequence;

CREATE SEQUENCE IF NOT EXISTS account_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS account_preferences_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS user_profile_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS role_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS address_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS farm_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS field_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS field_usage_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS crop_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS field_task_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS field_resource_application_sequence START WITH 1 INCREMENT BY 1;

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

CREATE TABLE account (
    id BIGINT NOT NULL DEFAULT nextval('account_sequence') PRIMARY KEY,
    public_id UUID NOT NULL UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(15) UNIQUE,
    email_verified BOOLEAN NOT NULL DEFAULT FALSE,
    phone_verified BOOLEAN NOT NULL DEFAULT FALSE,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
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
