create table subscription
(
    id           serial primary key,
    activity_id  int references users (id) not null,
    user_id      int references users (id) not null,
    created      timestamp not null
);