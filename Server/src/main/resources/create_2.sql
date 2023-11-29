drop table transaction;

drop table category;

drop table user_budget;

drop table budget;

drop table user_info;

drop table user;


CREATE TABLE user
(
    user_id   INT PRIMARY KEY AUTO_INCREMENT,
    user_role ENUM ('ADMIN', 'USER') NOT NULL DEFAULT 'USER',
    login     VARCHAR(255)           NOT NULL UNIQUE,
    password  VARCHAR(60)            NOT NULL
);

CREATE TABLE user_info
(
    user_id     INT PRIMARY KEY AUTO_INCREMENT,
    first_name  VARCHAR(255) NOT NULL,
    second_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (user_id)
);

CREATE TABLE budget
(
    budget_id         INT PRIMARY KEY AUTO_INCREMENT,
    budget_name       VARCHAR(255)   NOT NULL,
    initial_amount    DECIMAL(14, 2) NOT NULL DEFAULT 0,
    expected_income   DECIMAL(14, 2) NOT NULL DEFAULT 0,
    expected_spending DECIMAL(14, 2) NOT NULL DEFAULT 0,
    start_date        DATE           NOT NULL,
    end_date          DATE           NOT NULL,
    link              VARCHAR(36)    NOT NULL UNIQUE
);


CREATE TABLE user_budget
(
    user_id   INT                       NOT NULL,
    budget_id INT                       NOT NULL,
    role      ENUM ('MEMBER', 'LEADER') NOT NULL,
    PRIMARY KEY (user_id, budget_id),
    FOREIGN KEY (user_id) REFERENCES user (user_id),
    FOREIGN KEY (budget_id) REFERENCES budget (budget_id)
);


CREATE TABLE category
(
    category_id      INT PRIMARY KEY AUTO_INCREMENT,
    budget_id        INT                        NOT NULL,
    category_name    VARCHAR(255)               NOT NULL,
    category_type    ENUM ('INCOME', 'EXPENSE') NOT NULL,
    parent_category  INT,
    FOREIGN KEY (budget_id) REFERENCES budget (budget_id),
    FOREIGN KEY (parent_category) REFERENCES category (category_id)
);

CREATE TABLE transaction
(
    transaction_id   INT PRIMARY KEY AUTO_INCREMENT,
    amount           DECIMAL(14, 2) NOT NULL,
    comment          VARCHAR(255),
    date             DATE           NOT NULL,
    budget_id        INT            NOT NULL,
    category_id      INT            NOT NULL,
    user_id          INT            NOT NULL,
    FOREIGN KEY (budget_id) REFERENCES budget (budget_id),
    FOREIGN KEY (category_id) REFERENCES category (category_id),
    FOREIGN KEY (user_id) REFERENCES user (user_id)
);