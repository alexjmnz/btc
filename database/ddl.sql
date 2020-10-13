/* Create database artifacts for BTC transactions, the view is just created to hide the query
implementation from the application, I would select this if throughput for recording transactions
is too high, and the reads of transactions are less */

create schema btc;

create table btc.btc_transaction (
    id          bigserial constraint transaction_key primary key,
    amount      numeric(12,2),
    datetime    timestamp
);

create index on btc.btc_transaction (datetime);

create view btc.btc_transaction_view as
  select
    id,
    datetime,
    amount,
    (select sum(amount) as balance from btc.btc_transaction where datetime < t.datetime or (datetime = t.datetime and id <= t.id))
  from btc.btc_transaction t;