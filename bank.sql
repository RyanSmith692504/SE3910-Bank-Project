drop table if exists expenses;
drop table if exists users;

create table users (
	username varchar(100),
    name varchar(100),
    password varchar(100),
    total_balance double,
    savings_goal double,
    loan_amount double,
    primary key (username)
);

create table expenses (
	username varchar(100),
	expense_id char(5),
	planned int,
    amount double,
    income_or_expense int,
    information varchar(5000),
    due_date date,
	foreign key (username) references users(username),
    primary key (expense_id)
);

insert into users values ('nickw3', 'Nicholas Whorton', 'password', 1000, 200, 0);
insert into users values ('bretts4', 'Brett Smith', 'password', 2000, 300, 0);
insert into users values ('carterp5', 'Carter Putnam', 'password', 3000, 400, 0);
insert into users values ('rileyl6', 'Riley Lowe', 'password', 4000, 500, 0);
insert into users values ('joshb7', 'Josh Brown', 'password', 5000, 600, 0);

insert into expenses values ('nickw3', '12345', 1, 400, 0, 'rent', '2023-02-01');
insert into expenses values ('bretts4', '12346', 1, 15, 0, 'netflix', '2023-02-05');
insert into expenses values ('carterp5', '12347', 1, 100, 0, 'groceries', '2023-02-12');
insert into expenses values ('rileyl6', '12348', 1, 200, 0, 'car payment', '2023-02-09');
insert into expenses values ('joshb7', '12349', 1, 5, 0, 'spotify', '2023-02-01');


