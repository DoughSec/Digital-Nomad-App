-- database digital_nomad_app
BEGIN TRANSACTION;

-- *************************************************************************************************
-- Drop all db objects in the proper order
-- *************************************************************************************************
DROP TABLE IF EXISTS flights;
DROP TABLE IF EXISTS locations;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS lodgings;
DROP TABLE IF EXISTS trips;
DROP TABLE IF EXISTS users;

-- *************************************************************************************************
-- Create the tables and constraints
-- *************************************************************************************************

--flights
CREATE TABLE flights (
	flight_id SERIAL,
	flight_cost integer NOT NULL,
	travel_time integer NOT NULL,
	flight_start_time TIMESTAMP NOT NULL,
	flight_end_time TIMESTAMP NOT NULL,
	CONSTRAINT PK_flight PRIMARY KEY (flight_id)
);

--users (name is pluralized because 'user' is a SQL keyword)
CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

--reviews
CREATE TABLE reviews (
	review_id SERIAL,
	rating decimal NOT NULL,
	comment varchar(50) NOT NULL,
	CONSTRAINT PK_review PRIMARY KEY (review_id)
);

--locations
CREATE TABLE locations (
	location_id SERIAL,
	review_id INT NOT NULL,
	city varchar(50) NOT NULL,
	address varchar(50) NOT NULL,
	imgURL varchar(100) NOT NULL,
	CONSTRAINT PK_location PRIMARY KEY (location_id),
	CONSTRAINT FK_location_review FOREIGN KEY(review_id) REFERENCES reviews(review_id)
);

CREATE TABLE lodgings (
	lodging_id SERIAL,
	lodging_cost_per_night integer NOT NULL,
	total_lodging_cost integer NOT NULL,
	distance_from_airport integer NOT NULL,
	nights_to_stay integer NOT NULL,
	CONSTRAINT PK_lodging PRIMARY KEY (lodging_id)
);

--trips
CREATE TABLE trips (
	trip_id SERIAL,
	user_id INT NOT NULL,
	trip_cost INT NOT NULL,
	description varchar(500) NOT NULL,
	date_from date NOT NULL,
	date_to date NOT NULL,
	CONSTRAINT PK_trip PRIMARY KEY (trip_id),
	CONSTRAINT PK_trip_user FOREIGN KEY(user_id) REFERENCES users(user_id)
);

-- *************************************************************************************************
-- Insert some sample starting data
-- *************************************************************************************************


-- Users
-- Password for all users is password
INSERT INTO
    users (username, password_hash, role)
VALUES
    --1
    ('user', '$2y$10$TTc7bgBcYELeMeVUpJb4KeIQfvSJZlYZYKyJCRo8ZBOwFkVis190e','ROLE_USER'),
    --2
    ('admin','$2y$10$5FTtQt8/lwoeZi.LeRPaRuxioNuEJcVzg7XIa5NinhnJIKLXPt5jy','ROLE_ADMIN');


COMMIT TRANSACTION;
