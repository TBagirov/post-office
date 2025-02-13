

    -- Создание таблицы почтальонов
    CREATE TABLE IF NOT EXISTS Postmans
    (
        id         UUID    NOT NULL PRIMARY KEY,
        name       VARCHAR NOT NULL,
        surname    VARCHAR NOT NULL,
        patronymic VARCHAR NOT NULL
    );

    -- Создание таблицы участков
    CREATE TABLE IF NOT EXISTS Regions
    (
        id   UUID        NOT NULL PRIMARY KEY,
        name VARCHAR(40) NOT NULL UNIQUE
    );

    -- Создание таблицы улиц
    CREATE TABLE IF NOT EXISTS Streets
    (
        id        UUID        NOT NULL PRIMARY KEY,
        name      VARCHAR(50) NOT NULL UNIQUE,
        region_id UUID        NULL REFERENCES Regions (id) ON DELETE SET NULL
    );

    -- Создание таблицы отношений почтальонов к участкам
    CREATE TABLE IF NOT EXISTS Districts
    (
        id         UUID NOT NULL PRIMARY KEY,
        postman_id UUID NULL REFERENCES Postmans (id) ON DELETE SET NULL,
        region_id  UUID NULL REFERENCES Regions (id) ON DELETE SET NULL,
        CONSTRAINT unique_postman_region UNIQUE (postman_id, region_id)
    );

    -- Создание таблицы подписчиков
    CREATE TABLE IF NOT EXISTS Subscribers
    (
        id          UUID       NOT NULL PRIMARY KEY,
        name        VARCHAR    NOT NULL,
        surname     VARCHAR    NOT NULL,
        patronymic  VARCHAR    NOT NULL,
        district_id UUID       NULL REFERENCES Districts (id) ON DELETE SET NULL,
        street_id   UUID       NULL REFERENCES Streets (id) ON DELETE SET NULL,
        building    VARCHAR(5) NOT NULL,
        sub_address VARCHAR(5) NULL
    );

    -- Создание таблицы типов изданий
    CREATE TABLE IF NOT EXISTS Publication_types
    (
        id   UUID        NOT NULL PRIMARY KEY,
        name VARCHAR(10) NOT NULL UNIQUE
    );


    -- Создание таблицы изданий
    CREATE TABLE IF NOT EXISTS Publications
    (
        id                  UUID               NOT NULL PRIMARY KEY,
        index               VARCHAR(15) UNIQUE NOT NULL,
        title               VARCHAR(150)       NOT NULL,
        publication_type_id UUID               NULL REFERENCES Publication_types (id) ON DELETE SET NULL,
        price               INT                NOT NULL
    );

    -- Создание таблицы подписок
    CREATE TABLE IF NOT EXISTS Subscriptions
    (
        id             UUID NOT NULL PRIMARY KEY,
        subscriber_id  UUID NULL REFERENCES Subscribers (id) ON DELETE SET NULL,
        publication_id UUID NULL REFERENCES Publications (id) ON DELETE SET NULL,
        start_date     TIMESTAMP NOT NULL,
        duration       INT NOT NULL
    );


-------------------------
    CREATE TABLE IF NOT EXISTS  roles(
                                         id UUID NOT NULL PRIMARY KEY,
                                         name varchar(15) NOT NULL
    );


    CREATE TABLE IF NOT EXISTS  users(
        id UUID NOT NULL PRIMARY KEY,
        name VARCHAR NOT NULL,
        username varchar not null,
        email varchar null,
        password varchar not null,
        role_id UUID NOT NULL REFERENCES roles(id)
    );

    CREATE TABLE IF NOT EXISTS refresh_tokens(
        id UUID NOT NULL PRIMARY KEY,
        user_id UUID NOT NULL REFERENCES users(id),
        token varchar NOT NULL
    );

