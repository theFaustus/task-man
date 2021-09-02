create table users
(
    id bigint not null
        constraint users_pkey
            primary key,
    first_name varchar(255),
    last_name varchar(255),
    user_name varchar(255)
        constraint uk_k8d0f2n7n88w1a16yhua64onx
            unique
);

create table tasks
(
    id bigint not null
        constraint tasks_pkey
            primary key,
    description varchar(255),
    title varchar(255)
);

create table user_task
(
    user_id bigint not null
        constraint fkj6lai3y87ttxldkysg1549etg
            references users,
    task_id bigint not null
        constraint fkp7b0g1h9lxrklls4s1pw68nj2
            references tasks,
    constraint user_task_pkey
        primary key (user_id, task_id)
);

