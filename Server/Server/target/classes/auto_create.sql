DROP TABLE IF EXISTS transaction;
DROP TABLE IF EXISTS user_info;
DROP TABLE IF EXISTS user_budget;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS subcategory;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS budget;

CREATE TABLE user
(
    user_id   INT PRIMARY KEY AUTO_INCREMENT,
    login     VARCHAR(255) NOT NULL UNIQUE,
    password  VARCHAR(60)  NOT NULL,
    user_role ENUM ('USER', 'ADMIN')
);

create table user_info
(
    user_id     integer      not null auto_increment,
    first_name  varchar(255) not null,
    second_name varchar(255) not null,
    primary key (user_id)
);



CREATE TABLE budget
(
    budget_id         INT PRIMARY KEY AUTO_INCREMENT,
    budget_name       VARCHAR(255)             NOT NULL,
    end_date          DATE                     NOT NULL,
    expected_income   DECIMAL(14, 2) DEFAULT 0 NOT NULL,
    expected_spending DECIMAL(14, 2) DEFAULT 0 NOT NULL,
    initial_amount    DECIMAL(14, 2) DEFAULT 0 NOT NULL,
    link              VARCHAR(36)              NOT NULL,
    start_date        DATE                     NOT NULL
);

CREATE TABLE category
(
    category_id   INT PRIMARY KEY AUTO_INCREMENT,
    budget_id     INT                        NOT NULL,
    category_name VARCHAR(255)               NOT NULL,
    category_type ENUM ('INCOME', 'EXPENSE') NOT NULL,
    FOREIGN KEY (budget_id) REFERENCES budget (budget_id)
);

CREATE TABLE subcategory
(
    subcategory_id    INT PRIMARY KEY AUTO_INCREMENT,
    subcategory_name  VARCHAR(255)   NOT NULL,
    subcategory_value DECIMAL(14, 2) NOT NULL,
    category_id       INT            NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category (category_id)
);

CREATE TABLE transaction
(
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    amount         DECIMAL(14, 2) NOT NULL,
    comment        VARCHAR(255),
    date           DATE           NOT NULL,
    budget_id      INT            NOT NULL,
    subcategory_id INT            NOT NULL,
    user_id        INT            NOT NULL,
    FOREIGN KEY (budget_id) REFERENCES budget (budget_id),
    FOREIGN KEY (subcategory_id) REFERENCES subcategory (subcategory_id),
    FOREIGN KEY (user_id) REFERENCES user (user_id)
);


CREATE TABLE user_budget
(
    role      ENUM ('MEMBER', 'LEADER') NOT NULL,
    user_id   INT                       NOT NULL,
    budget_id INT                       NOT NULL,
    PRIMARY KEY (budget_id, user_id),
    FOREIGN KEY (user_id) REFERENCES user (user_id),
    FOREIGN KEY (budget_id) REFERENCES budget (budget_id)
);
