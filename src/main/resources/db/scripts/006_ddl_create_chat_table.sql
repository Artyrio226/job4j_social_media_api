create table chat
(
    id        serial primary key,
    user1_id  int references users (id) not null,
    user2_id  int references users (id) not null
);