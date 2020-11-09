DROP TABLE IF EXISTS user_locations;
DROP TABLE IF EXISTS persistent_logins;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS verif_tokens;

CREATE TABLE users(
    username varchar(50) NOT NULL PRIMARY KEY,
    password varchar(100) NOT NULL,
    enabled boolean NOT NULL DEFAULT false,
    first_name varchar(30) ,
    last_name varchar(30) ,
    email varchar(80) ,
    phone_number varchar(20)
);

CREATE TABLE locations(
    location_id serial PRIMARY KEY,
    latitude numeric(18, 16) NOT NULL,
    longitude numeric(18, 16) NOT NULL,
    location_date timestamp without time zone NOT NULL
);

CREATE TABLE user_locations
(
    username varchar(50) NOT NULL REFERENCES users (username),
    location_id integer NOT NULL REFERENCES locations (location_id)
);

CREATE TABLE authorities(
  authority_id serial primary key,
  username varchar(50) NOT NULL REFERENCES users (username),
  authority varchar(50) NOT NULL DEFAULT 'ROLE_USER'
);

CREATE TABLE persistent_logins(
 username varchar(50) NOT NULL
          REFERENCES users (username),
 series varchar(64) PRIMARY KEY,
 token varchar(64) NOT NULL,
 last_used timestamp NOT NULL
);

CREATE TABLE verif_tokens(
 token varchar(50) NOT NULL PRIMARY KEY,
 username varchar(64) ,
 expiry_date timestamp without time zone NOT NULL
);
