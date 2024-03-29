drop table if exists RESERVES CASCADE;
create table RESERVES (
    id bigint generated by default as identity,
    create_at timestamp,
    update_at timestamp,
    amount bigint not null,
    balance bigint not null,
    expired_at bigint not null,
    member_id varchar(32) not null,
    status varchar(6) not null,
    primary key (id)
);
drop table if exists reserves_history CASCADE;
create table reserves_history (
    id bigint generated by default as identity,
    create_at timestamp,
    history_id bigint,
    reserves_id bigint,
    primary key (id)
);