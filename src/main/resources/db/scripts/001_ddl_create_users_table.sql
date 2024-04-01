create table users
(
    id        serial primary key,
    username  varchar not null,
    email     varchar not null unique,
    password  varchar not null
);