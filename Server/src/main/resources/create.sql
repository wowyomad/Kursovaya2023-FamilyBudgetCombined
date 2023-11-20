use budget_db;

DROP TABLE IF EXISTS user_budgets;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS subcategories;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS budgets;
DROP TABLE IF EXISTS user_info;
DROP TABLE IF EXISTS user;

CREATE TABLE user
(
    user_id  INT PRIMARY KEY AUTO_INCREMENT,
    login    VARCHAR(255) NOT NULL UNIQUE,
    password CHAR(60)     NOT NULL
);

CREATE TABLE user_info
(
    user_id     INT          NOT NULL UNIQUE,
    first_name  VARCHAR(255) NOT NULL,
    second_name VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (user_id)
);

CREATE TABLE budgets
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

CREATE TABLE categories
(
    category_id   INT PRIMARY KEY AUTO_INCREMENT,
    category_name VARCHAR(255)                NOT NULL UNIQUE,
    category_type ENUM ('INCOME', 'SPENDING') NOT NULL,
    budget_id     INT                         NOT NULL,
    FOREIGN KEY (budget_id) REFERENCES budgets (budget_id)
);

CREATE TABLE subcategories
(
    subcategory_id    INT PRIMARY KEY AUTO_INCREMENT,
    subcategory_name  VARCHAR(255)   NOT NULL UNIQUE,
    subcategory_value DECIMAL(14, 2) NOT NULL,
    category_id       INT            NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories (category_id)
);

CREATE TABLE transactions
(
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    date           DATE           NOT NULL,
    amount         DECIMAL(14, 2) NOT NULL,
    category_id    INT            NOT NULL,
    subcategory_id INT            NOT NULL,
    comment        VARCHAR(255),
    user_id        INT            NOT NULL,
    budget_id      INT            NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories (category_id),
    FOREIGN KEY (subcategory_id) REFERENCES subcategories (subcategory_id),
    FOREIGN KEY (user_id) REFERENCES user_info (user_id),
    FOREIGN KEY (budget_id) REFERENCES budgets (budget_id)
);

CREATE TABLE user_budgets
(
    user_id   INT          NOT NULL,
    budget_id INT          NOT NULL,
    role      VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id, budget_id),
    FOREIGN KEY (user_id) REFERENCES user_info (user_id),
    FOREIGN KEY (budget_id) REFERENCES budgets (budget_id)
);
