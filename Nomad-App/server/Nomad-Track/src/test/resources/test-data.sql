BEGIN TRANSACTION;

-Users
INSERT INTO users (username, password_hash, role) VALUES ('user1','user1','ROLE_USER');
INSERT INTO users (username, password_hash, role) VALUES ('user2','user2','ROLE_USER');
INSERT INTO users (username, password_hash, role) VALUES ('user3','user3','ROLE_USER');

INSERT INTO
    services (service_name, description, cost, length_minutes)
VALUES
    --1
    ('service 1', 'description 1', 10, 10),
    --2
    ('service 2', 'description 2', 20, 20),
    --3
    ('service 3', 'description 3', 30, 30);


INSERT INTO
    clients (user_id, first_name, last_name, phone_number)
VALUES
    --1
    (1, 'client', '1', '1111111111'),
    --2
    (2, 'client', '2', '2222222222'),
    --3
    (3, 'client', '3', '3333333333');

INSERT INTO
    bookings (user_id, service_id, appointment_date, appointment_time)
VALUES
    --1
    (2, 8, '2025-01-01', '08:30:00'),
    --2
    (4, 5, '2025-01-01', '10:00:00'),
    --3
    (5, 10, '2025-02-19', '14:00:00');

COMMIT TRANSACTION;
