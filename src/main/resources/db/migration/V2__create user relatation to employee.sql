ALTER TABLE employees
    ADD user_id BIGINT default 1;

ALTER TABLE employees
    ALTER COLUMN user_id SET NOT NULL;

ALTER TABLE employees
    ADD CONSTRAINT employees_user_id_unique UNIQUE (user_id);

ALTER TABLE employees
    ADD CONSTRAINT FK_EMPLOYEES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);