create table friendship
(
    id            serial primary key,
    status        boolean not null,
    created       timestamp not null,
    user_from_id  int references users (id) not null,
    user_to_id    int references users (id) not null
);