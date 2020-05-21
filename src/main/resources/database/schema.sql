CREATE DATABASE IF NOT EXISTS shop
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE shop;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS carts;
DROP TABLE IF EXISTS cart_items;

CREATE TABLE users
(
    id           BIGINT(20)              NOT NULL AUTO_INCREMENT,
    first_name   VARCHAR(50)             NOT NULL,
    last_name    VARCHAR(50)             NOT NULL,
    email        VARCHAR(254)            NOT NULL,
    phone_number VARCHAR(20)             NOT NULL,
    password     VARCHAR(130)            NOT NULL,
    role_type    VARCHAR(20)             NOT NULL,
    created_on   TIMESTAMP DEFAULT now() NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT users_email_AK UNIQUE (email)
);

CREATE TABLE products
(
    id         BIGINT(20)              NOT NULL AUTO_INCREMENT,
    name       VARCHAR(150)            NOT NULL,
    image      VARCHAR(255)            NOT NULL,
    price      DECIMAL                 NOT NULL,
    created_on TIMESTAMP DEFAULT now() NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE carts
(
    user_id    BIGINT(20)              NOT NULL,
    created_on TIMESTAMP DEFAULT now() NOT NULL,
    PRIMARY KEY (user_id),
    CONSTRAINT carts_user_id_AK UNIQUE (user_id)
);

CREATE TABLE cart_items
(
    id         BIGINT(20)              NOT NULL AUTO_INCREMENT,
    product_id BIGINT(10)              NOT NULL UNIQUE,
    quantity   INT(10)                 NOT NULL,
    created_on TIMESTAMP DEFAULT now() NOT NULL,
    cart_id    BIGINT(20)              NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT cart_items_product_FK FOREIGN KEY (product_id) REFERENCES products (id),
    CONSTRAINT cart_items_cart_FK FOREIGN KEY (cart_id) REFERENCES carts (user_id)
);
