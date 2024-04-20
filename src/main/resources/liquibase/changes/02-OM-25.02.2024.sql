--liquibase formatted sql
--changeset kalendarev:OM-25.02.2024 splitStatements:true endDelimiter:;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

insert into om_roles (id, role, description)
values (uuid_generate_v4(), 'ADMIN', 'Администратор'),
       (uuid_generate_v4(), 'MANAGER', 'Менеджер'),
       (uuid_generate_v4(), 'TECHNOLOGIST', 'Технолог'),
       (uuid_generate_v4(), 'CEO', 'Главный Операционный директор')