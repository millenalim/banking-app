create database if not exists banking_app;

use banking_app;

drop table if exists checking_account;
create table checking_account (
	id int primary key auto_increment not null,
    account_balance decimal(19,2) null,
    account_number int not null,
    routing_number int not null
);

drop table if exists checking_transaction;
create table checking_transaction (
	id int primary key auto_increment not null,
    amount double not null,
    available_balance decimal(19,2) null,
    `date` datetime null,
	`description` varchar(255) null,
    `status` varchar(255) null,
    `type` varchar(255) null,
    checking_account_id int null,
    constraint fk_checking_transaction_checking_account_id
		foreign key (checking_account_id) 
        references checking_account(id)
);

drop table if exists savings_account;
create table savings_account (
	id int primary key auto_increment not null,
    account_balance decimal(19,2) null,
    account_number int not null,
    routing_number int not null
);

drop table if exists savings_transaction;
create table savings_transaction (
	id int primary key auto_increment not null,
    amount double not null,
    available_balance decimal(19,2) null,
    `date` datetime null,
	`description` varchar(255) null,
    `status` varchar(255) null,
    `type` varchar(255) null,
    savings_account_id int null,
    constraint fk_savings_transaction_savings_account_id
		foreign key (savings_account_id) 
        references savings_account(id)
);

drop table if exists app_user;
create table app_user (
	app_user_id int primary key auto_increment not null,
    email varchar(255) not null unique,
    username varchar(255) null,
    first_name varchar(255) null,
    last_name varchar(255) null,
	phone varchar(255) null,
    password_hash varchar(2048) not null,
    enabled bit not null default(1),
    checking_account_id int null,
    savings_account_id int null,
    constraint app_user_checking_account_id
		foreign key (checking_account_id)
        references checking_account(id),
	constraint app_user_savings_account_id
		foreign key (savings_account_id)
        references savings_account(id)
);

drop table if exists app_role;
create table app_role (
	app_role_id int primary key auto_increment,
    `name` varchar(50) not null unique
);

drop table if exists app_user_role;
create table app_user_role (
	app_user_id int not null,
    app_role_id int not null,
    constraint pk_app_user_role
		primary key (app_user_id, app_role_id),
	constraint fk_app_user_role_user_id
		foreign key (app_user_id)
        references app_user(app_user_id),
	constraint fk_app_user_role_role_id
		foreign key (app_role_id)
        references app_role(app_role_id)
);

drop table if exists recipient;
create table recipient (
	id int primary key auto_increment not null,
    account_number varchar(255) not null,
    `description` varchar(255) null,
    email varchar(255) null,
    `name` varchar(255) null,
    phone varchar(255) null,
    app_user_id int null,
   constraint fk_recipient_app_user_id
		foreign key (app_user_id)
        references app_user(app_user_id)
);

