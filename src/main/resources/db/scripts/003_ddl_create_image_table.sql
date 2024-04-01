create table image
(
    id      serial primary key,
    name    varchar not null,
    path    varchar not null unique,
    post_id int references posts (id) on delete cascade
);