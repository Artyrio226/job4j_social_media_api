create table chat
(
    id        serial primary key,
    first_user_id  int references users (id) not null,
    second_user_id  int references users (id) not null
);