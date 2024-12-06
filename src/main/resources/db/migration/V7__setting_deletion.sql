ALTER TABLE Subscribers ALTER COLUMN district_id DROP NOT NULL;
ALTER TABLE Subscribers ALTER COLUMN street_id DROP NOT NULL;
ALTER TABLE Districts ALTER COLUMN region_id DROP NOT NULL;
ALTER TABLE Districts ALTER COLUMN postman_id DROP NOT NULL;
ALTER TABLE Streets ALTER COLUMN region_id DROP NOT NULL;
ALTER TABLE Publications ALTER COLUMN publication_type_id DROP NOT NULL;
ALTER TABLE Subscriptions ALTER COLUMN subscriber_id DROP NOT NULL;
ALTER TABLE Subscriptions ALTER COLUMN publication_id DROP NOT NULL;


-- Удаление существующих ограничений внешних ключей
ALTER TABLE Streets DROP CONSTRAINT Streets_region_id_fkey;
ALTER TABLE Districts DROP CONSTRAINT Districts_postman_id_fkey;
ALTER TABLE Districts DROP CONSTRAINT Districts_region_id_fkey;
ALTER TABLE Subscribers DROP CONSTRAINT Subscribers_district_id_fkey;
ALTER TABLE Subscribers DROP CONSTRAINT Subscribers_street_id_fkey;
ALTER TABLE Publications DROP CONSTRAINT Publications_publication_type_id_fkey;
ALTER TABLE Subscriptions DROP CONSTRAINT Subscriptions_subscriber_id_fkey;
ALTER TABLE Subscriptions DROP CONSTRAINT Subscriptions_publication_id_fkey;

-- Добавление новых ограничений с ON DELETE SET NULL
ALTER TABLE Streets
    ADD CONSTRAINT Streets_region_id_fkey FOREIGN KEY (region_id) REFERENCES Regions (id) ON DELETE SET NULL;

ALTER TABLE Districts
    ADD CONSTRAINT Districts_postman_id_fkey FOREIGN KEY (postman_id) REFERENCES Postmans (id) ON DELETE SET NULL;

ALTER TABLE Districts
    ADD CONSTRAINT Districts_region_id_fkey FOREIGN KEY (region_id) REFERENCES Regions (id) ON DELETE SET NULL;

ALTER TABLE Subscribers
    ADD CONSTRAINT Subscribers_district_id_fkey FOREIGN KEY (district_id) REFERENCES Districts (id) ON DELETE SET NULL;

ALTER TABLE Subscribers
    ADD CONSTRAINT Subscribers_street_id_fkey FOREIGN KEY (street_id) REFERENCES Streets (id) ON DELETE SET NULL;

ALTER TABLE Publications
    ADD CONSTRAINT Publications_publication_type_id_fkey FOREIGN KEY (publication_type_id) REFERENCES Publication_types (id) ON DELETE SET NULL;

ALTER TABLE Subscriptions
    ADD CONSTRAINT Subscriptions_subscriber_id_fkey FOREIGN KEY (subscriber_id) REFERENCES Subscribers (id) ON DELETE SET NULL;

ALTER TABLE Subscriptions
    ADD CONSTRAINT Subscriptions_publication_id_fkey FOREIGN KEY (publication_id) REFERENCES Publications (id) ON DELETE SET NULL;
