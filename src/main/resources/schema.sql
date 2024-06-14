-- USE IN DEVELOPMENT...
--DROP SEQUENCE IF EXISTS account_sequence;
--DROP SEQUENCE IF EXISTS role_sequence;
--DROP SEQUENCE IF EXISTS farm_sequence;
--DROP SEQUENCE IF EXISTS field_sequence;

CREATE SEQUENCE IF NOT EXISTS account_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS role_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS farm_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS field_sequence START WITH 1 INCREMENT BY 1;

--CREATE TABLE IF NOT EXISTS account (
--    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('account_sequence'),
--    name VARCHAR(255)
--);

---- Create the account table
--CREATE TABLE IF NOT EXISTS account (
--    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('account_sequence'),
--    public_id UUID NOT NULL UNIQUE,
--    first_name VARCHAR(255) NOT NULL,
--    last_name VARCHAR(255) NOT NULL,
--    email VARCHAR(255) NOT NULL UNIQUE,
--    password VARCHAR(255) NOT NULL,
--    is_verified BOOLEAN NOT NULL,
--    date_created TIMESTAMP NOT NULL,
--    last_updated TIMESTAMP NOT NULL
--);
