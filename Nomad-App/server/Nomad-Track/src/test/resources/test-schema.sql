BEGIN TRANSACTION;

-- *************************************************************************************************
-- Drop all db objects in the proper order
-- *************************************************************************************************
DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS clients;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS services;


-- *************************************************************************************************
-- Create the tables and constraints
-- *************************************************************************************************

--services
CREATE TABLE services (
	service_id SERIAL,
	service_name varchar(50) NOT NULL UNIQUE,
	description varchar NOT NULL,
	cost integer NOT NULL,
	length_minutes integer NOT NULL,
	CONSTRAINT PK_service PRIMARY KEY (service_id)
);

--users (name is pluralized because 'user' is a SQL keyword)
CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

--clients
CREATE TABLE clients (
	client_id SERIAL,
	user_id INT NOT NULL,
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	phone_number varchar(10) NOT NULL,
	CONSTRAINT PK_client PRIMARY KEY (client_id),
	CONSTRAINT FK_client_user FOREIGN KEY(user_id) REFERENCES users(user_id)
);

--bookings
CREATE TABLE bookings (
	booking_id SERIAL,
	user_id INT NOT NULL,
	service_id INT NOT NULL,
	appointment_date date NOT NULL,
	appointment_time time NOT NULL,
	CONSTRAINT PK_booking PRIMARY KEY (booking_id),
	CONSTRAINT PK_booking_user FOREIGN KEY(user_id) REFERENCES users(user_id),
	CONSTRAINT PK_booking_service FOREIGN KEY(service_id) REFERENCES services(service_id)
);


COMMIT TRANSACTION;
