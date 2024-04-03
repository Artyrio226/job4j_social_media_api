create table activity
(
    id      serial primary key,
    user_id int references users (id) not null
);