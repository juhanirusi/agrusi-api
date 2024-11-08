---------------------------------------------------------------------
-- USE IN DEVELOPMENT RUN ONLY ONCE...

--DROP TABLE IF EXISTS "account_link_role";
--DROP TABLE IF EXISTS "role";
--DROP TABLE IF EXISTS "field_resource_application";
--DROP TABLE IF EXISTS "field_task";
--DROP TABLE IF EXISTS "field_usage";
--DROP TABLE IF EXISTS "crop";
--DROP TABLE IF EXISTS "field";
--DROP TABLE IF EXISTS "farm_types";
--DROP TABLE IF EXISTS "address_types";
--DROP TABLE IF EXISTS "address";
--DROP TABLE IF EXISTS "farm";
--DROP TABLE IF EXISTS "account";
--DROP TABLE IF EXISTS "account_preferences";
--DROP TABLE IF EXISTS "user_profile";
--
--DROP SEQUENCE IF EXISTS account_sequence;
--DROP SEQUENCE IF EXISTS account_preferences_sequence;
--DROP SEQUENCE IF EXISTS user_profile_sequence;
--DROP SEQUENCE IF EXISTS role_sequence;
--DROP SEQUENCE IF EXISTS address_sequence;
--DROP SEQUENCE IF EXISTS farm_sequence;
--DROP SEQUENCE IF EXISTS field_sequence;
--DROP SEQUENCE IF EXISTS field_usage_sequence;
--DROP SEQUENCE IF EXISTS crop_sequence;
--DROP SEQUENCE IF EXISTS field_task_sequence;
--DROP SEQUENCE IF EXISTS field_resource_application_sequence;
---------------------------------------------------------------------

-- KEEP WHAT'S BELOW ALWAYS UNCOMMENTED...

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

CREATE TABLE IF NOT EXISTS role (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('role_sequence'),
    authority VARCHAR(255) NOT NULL
);

-- KEEP WHAT'S ABOVE ALWAYS UNCOMMENTED...


---- Create the account table
--CREATE TABLE IF NOT EXISTS account (
--    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('account_sequence'),
--    public_id UUID NOT NULL UNIQUE,
--    email VARCHAR(255) NOT NULL UNIQUE,
--    password VARCHAR(255) NOT NULL,
--    is_verified BOOLEAN NOT NULL,
--    date_created TIMESTAMP NOT NULL,
--    last_updated TIMESTAMP NOT NULL
--);
--
--CREATE TABLE IF NOT EXISTS user_profile (
--    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('user_profile_sequence'),
--    first_name VARCHAR(255) NOT NULL,
--    last_name VARCHAR(255) NOT NULL,
--);
--
--CREATE TABLE IF NOT EXISTS farm (
--    id BIGINT NOT NULL DEFAULT nextval('farm_sequence') PRIMARY KEY,
--    public_id UUID NOT NULL UNIQUE,
--    name VARCHAR(255) NOT NULL,
--    date_created TIMESTAMP NOT NULL,
--    last_updated TIMESTAMP NOT NULL
--);
