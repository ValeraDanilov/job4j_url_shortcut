create table auth_request
(
    id serial primary key,
    login text not null,
    password text not null,
    status boolean
);