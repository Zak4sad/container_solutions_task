DROP TABLE IF EXISTS people;
create table people (
        uuid varchar(255) not null,
        age integer,
        fare double,
        name varchar(255),
        parents_or_children_aboard integer,
        passenger_class integer,
        sex varchar(255),
        siblings_or_spouses_aboard integer,
        survived boolean,
        primary key (uuid)
    )
