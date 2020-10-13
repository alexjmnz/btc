create table btc_transaction (
    id          bigserial constraint transaction_key primary key,
    amount      numeric(12,2),
    datetime    timestamp
);

create view btc_transaction_view as
   select datetime, amount from btc_transaction;

insert into btc_transaction (datetime, amount) values ('2020-10-05 10:15:31', 23.0);
insert into btc_transaction (datetime, amount) values ('2020-10-05 10:30:31', 43.0);
insert into btc_transaction (datetime, amount) values ('2020-10-05 11:12:03', 53.0);