--changeset artei:1

CREATE SEQUENCE products_id_seq;

CREATE TABLE products (
    id BIGINT PRIMARY KEY DEFAULT nextval('products_id_seq'),
    name VARCHAR(255),
    sku VARCHAR(255) NOT NULL UNIQUE,
    price NUMERIC(19, 4),
    description VARCHAR(255)
);
