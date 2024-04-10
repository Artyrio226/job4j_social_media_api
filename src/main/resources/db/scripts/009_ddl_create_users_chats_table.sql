create table users_chats
(
    id       serial primary key,
    user_id  int references users (id) not null,
    chat_id  int references users (id) not null
);