/* Create database artifacts for BTC transactions, the normalized view approach helps to have
better performance when querying transactions, usually I would select this if reads are the priority
and the throughput for register a transactions is not high */

create schema btc;

create table btc.btc_transaction (
    id          bigserial constraint transaction_key primary key,
    amount      numeric(12,2),
    datetime    timestamp
);

create index on btc.btc_transaction (datetime);

create materialized view btc.btc_transaction_view as
  select
    id,
    datetime,
    (select sum(amount) as amount from btc.btc_transaction where datetime < t.datetime or (datetime = t.datetime and id <= t.id))
  from btc.btc_transaction t
with data;

create index on btc.btc_transaction_view (datetime);

create or replace function refresh_btc_transactions_view()
returns trigger language plpgsql
as $$
begin
	refresh materialized view btc.btc_transaction_view;
  return null;
end $$;

create trigger btc_view_refresh
    after insert
    on btc.btc_transaction
    for each row
    execute procedure refresh_btc_transactions_view();