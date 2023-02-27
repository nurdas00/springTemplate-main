drop all objects delete files;
create table balance
(
    id      int primary key,
    kgs     int,
    kzt     int,
    rub     int,
    usd     int,
    eur     int,
    rmb     int,
    user_id int
);

create table users
(
    id                int primary key,
    name              varchar,
    lastname          varchar,
    username          varchar,
    phone_number      varchar,
    password          varchar,
    confirmation_code varchar
);

insert into users (id, name, lastname, username, phone_number, password, confirmation_code)
values (1, 'tom', 'reddle', 'voldemort', '0500123456', 'tom123', '');
insert into users (id, name, lastname, username, phone_number, password, confirmation_code)
values (2, 'harry', 'potter', 'griffindor2000', '0700123456', 'hp123', '');

insert into balance (id, kgs, kzt, rub, usd, eur, rmb)
values (1, 1000, 10000, 500, 3000, 4000, 2000);
insert into balance (id, kgs, kzt, rub, usd, eur, rmb)
values (2, 2000, 20000, 1000, 5000, 3000, 1000);

alter table users
    add balance_id int;

alter table users
    add foreign key (balance_id)
        references balance (id);

alter table balance
    add foreign key (user_id)
        references users (id);

update users
set users.balance_id = 1
where id = 1;
update users
set users.balance_id = 2
where id = 2;

update balance
set balance.user_id = 1
where id = 1;
update balance
set balance.user_id = 2
where id = 2;

create table transactions
(
    id                int primary key,
    receiver_id       int,
    sender_id         int,
    currency          varchar,
    amount            int,
    confirmation_code varchar,
    comment           varchar,
    status            varchar
);

insert into transactions (id, receiver_id, sender_id, currency, amount, confirmation_code, comment, status)
values (1, 1, 2, 'KGS', 100, 'abc123', 'thank you', 'CREATED');