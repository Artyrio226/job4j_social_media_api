create table users_friendships
(
    id             serial primary key,
    user_id        int references users (id) not null,
    friendship_id  int references users (id) not null
);