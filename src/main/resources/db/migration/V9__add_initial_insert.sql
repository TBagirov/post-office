CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT into roles(id, name)
values (uuid_generate_v4(), 'Guest'),
       (uuid_generate_v4(),'Admin')
