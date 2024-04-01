create table activity
(
    id               serial primary key,
    subscription_id  int references users (id) not null,
    user_id          int references users (id) not null
);