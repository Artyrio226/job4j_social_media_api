create table subscription
(
    id       serial primary key,
    user_id  int references users (id) not null,
    created  timestamp not null
);