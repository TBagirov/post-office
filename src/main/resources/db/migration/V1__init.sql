drop table if exists Subscriptions;
drop table if exists Publications;
drop table if exists Publication_types;
drop table if exists Subscribers;
drop table if exists Districts;
drop table if exists Streets;
drop table if exists Regions;
drop table if exists Postmans;


-- подключение к нужной БД
--use PostOffice
--go


-- таблица данных почтальонов
create table Postmans
(
    id         uuid    not null primary key,
    name       varchar not null, -- Имя персоны
    surname    varchar not null, -- Фамилия персоны
    patronymic varchar not null  -- Отчество персоны

);

-- таблица участков, которая хранит в себе адрес проживания и название участка закрепленным за ним
create table Regions
(
    id   uuid        not null primary key,
    name varchar(40) not null -- название участка

);

-- таблица названий улиц
create table Streets
(
    id        uuid        not null primary key,
    name      varchar(50) not null,
    region_id uuid        not null REFERENCES Regions (id) -- id, внешний ключ к таблицам регионов

);




-- таблица отношений почтальонов к участкам (у нескольких участков может быть один почтальон)
create table Districts
(
    id         uuid not null primary key,
    postman_id uuid not null REFERENCES Postmans (id), -- id, внешний ключ к таблице почтальонов
    region_id  uuid not null REFERENCES Regions (id)   -- id, внешний ключ к таблице участков
);


-- таблица данных подписчиков
create table Subscribers
(
    id          uuid       not null primary key,
    name        varchar    not null,                           -- Имя персоны
    surname     varchar    not null,                           -- Фамилия персоны
    patronymic  varchar    not null,                           -- Отчество персоны
    district_id uuid       not null REFERENCES Districts (id), -- id, внешний ключ к таблице участков
    building    varchar(5) not null,                           -- номер дома
    sub_address int        null                                -- дополнительный столбец, номер квартиры, может быть null

);


-- таблица типов изданий
create table Publication_types
(
    id   uuid        not null primary key,
    name varchar(10) not null -- вид издания газета/журнал
);


-- таблица изданий
create table Publications
(
    id                  uuid               not null primary key,
    index               varchar(15) Unique not null,                                   -- индекс издания
    title               varchar(150)       not null,                                   -- название издания,
    publication_type_id uuid               not null REFERENCES Publication_types (id), -- id, внешний ключ к таблицы типов изданий
    price               int                not null                                    -- подписная цена, стоимость подписки

);


-- таблица подписок на издания
create table Subscriptions
(
    id             uuid not null primary key ,
    subscriber_id  uuid not null REFERENCES Subscribers (id),  -- id, внешний ключ к таблицам подписчиков
    publication_id uuid not null REFERENCES Publications (id), -- id, внешний ключ к таблице изданий
    start_date     Date not null,                              -- дата начала подписки
    duration       int  not null                               -- длительность подписки

);


