CREATE TABLE account (
    id SERIAL PRIMARY KEY,
    username VARCHAR NOT NULL,
    email VARCHAR NOT NULL UNIQUE,
    phone VARCHAR NOT NULL UNIQUE
);

CREATE TABLE ticket (
    id SERIAL PRIMARY KEY,
    session_id INT NOT NULL,
    row INT NOT NULL,
    cell INT NOT NULL,
    account_id INT NOT NULL REFERENCES account(id),
    CONSTRAINT unique_ticket UNIQUE (session_id, row, cell)
);

INSERT INTO account(id, username, email, phone) VALUES (0, 'somebody', 'some@email.ru', 0);

INSERT INTO ticket(session_id, row, cell, account_id) VALUES (1, 1, 1, 0);
INSERT INTO ticket(session_id, row, cell, account_id) VALUES (1, 1, 2, 0);
INSERT INTO ticket(session_id, row, cell, account_id) VALUES (1, 1, 3, 0);
INSERT INTO ticket(session_id, row, cell, account_id) VALUES (1, 2, 1, 0);
INSERT INTO ticket(session_id, row, cell, account_id) VALUES (1, 2, 2, 0);
INSERT INTO ticket(session_id, row, cell, account_id) VALUES (1, 2, 3, 0);
INSERT INTO ticket(session_id, row, cell, account_id) VALUES (1, 3, 1, 0);
INSERT INTO ticket(session_id, row, cell, account_id) VALUES (1, 3, 2, 0);
INSERT INTO ticket(session_id, row, cell, account_id) VALUES (1, 3, 3, 0);

INSERT INTO account(username, email, phone) VALUES ('Николай', 'mail@mail.ru', '+74951112233');
UPDATE ticket SET account_id = 0 WHERE id = 5;
