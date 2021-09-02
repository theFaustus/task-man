
-- -----------------------------------------------------
-- Table `taskmanager`.`user`
-- -----------------------------------------------------
CREATE TABLE "users"
(
    id        serial primary key,
    firstName VARCHAR(45) NULL,
    last_name VARCHAR(45) NULL,
    username  VARCHAR(45) NULL
);
-- -----------------------------------------------------
-- Table taskmanager.tasks
-- -----------------------------------------------------
CREATE TABLE tasks
(
    id          serial primary key,
    title       VARCHAR(256) NOT NULL,
    description VARCHAR(256) NOT NULL,
    user_id     int,
    FOREIGN KEY (user_id) REFERENCES users (id)
        ON DELETE CASCADE
);

