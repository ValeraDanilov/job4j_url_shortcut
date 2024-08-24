create table url_registration
(
    id    serial primary key,
    code text not null unique,
    url   text not null unique,
    total int
);
