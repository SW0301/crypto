DROP TABLE IF EXISTS pguser CASCADE;
DROP TABLE IF EXISTS pgadmin;
DROP TABLE IF EXISTS transaction;
DROP TABLE IF EXISTS exchange_rate;

CREATE TABLE pguser (
    id bigserial PRIMARY KEY,
    username varchar(50) NOT NULL,
    email varchar(50) NOT NULL,
    secret_key varchar(50) NOT NULL,
    RUB_wallet numeric NOT NULL,
    BTC_wallet numeric NOT NULL,
    TON_wallet numeric NOT NULL
);

CREATE TABLE transaction(
    id bigserial PRIMARY KEY,
    user_id bigint NOT NULL,
    currency_from varchar(10) NOT NULL,
    currency_to varchar(10) NOT NULL,
    amount_from numeric NOT NULL,
    amount_to numeric NOT NULL,
    date_of date NOT NULL
);

CREATE TABLE pgadmin(
    id bigserial PRIMARY KEY,
    secret_key varchar(50) NOT NULL
);
CREATE TABLE exchange_rate (
    id bigserial PRIMARY KEY,
    currency varchar(25) NOT NULL,
    currency_in_RUB numeric NOT NULL,
    currency_in_ton numeric NOT NULL,
    currency_in_btc numeric NOT NULL
);

ALTER TABLE transaction
    ADD CONSTRAINT FK_transaction_user FOREIGN KEY (user_id) REFERENCES pguser(id);

INSERT INTO pgadmin(secret_key)
VALUES
    ('erDT5XSfhyYzxWf6ZebKPg=='),
    ('gQBrrc7buxFGrgraD4Kwhg=='),
    ('YsXjWahDrlwxEYeQLH4Y7A=='),
    ('jVZb92oPZp2G26Jm05kIfw==');

INSERT INTO exchange_rate (currency, currency_in_rub, currency_in_ton, currency_in_btc)
VALUES
    ('TON', 180, 1, 0.00009564),
    ('BTC', 1882057.72, 10455.876202426, 1),
    ('RUB', 1, 0.005555556, 0.000000531333536);
