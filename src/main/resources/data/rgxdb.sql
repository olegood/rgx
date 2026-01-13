create table organization
(
    id                  bigint       not null
        constraint pk_organization primary key,
    code                varchar(15)  not null
        constraint uk_organization_code unique,
    name                varchar(64)  not null,
    website             varchar(128),
    country             varchar(64)  not null,
    description         varchar(300),
    founded             integer,
    industry            varchar(100) not null,
    number_of_employees integer,
    status              varchar(10)  not null
);

create table enrollment
(
    id              bigint      not null
        constraint pk_enrollment primary key,
    organization_id bigint      not null references organization (id),
    type            varchar(14) not null,
    status          varchar(14) not null
);

create table marker
(
    id            bigint      not null
        constraint pk_marker primary key,
    enrollment_id bigint      not null references enrollment (id),
    type          varchar(10) not null,
    start_date    date        not null,
    end_date      date
);
