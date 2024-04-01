create table message
(
    id          serial primary key,
    text        varchar not null,
    created_at  timestamp not null,
    is_read     boolean not null,
    chat_id     int references chat (id) not null,
    sender_id   int references users (id) not null
);