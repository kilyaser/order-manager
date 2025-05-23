--liquibase formatted sql
--changeset kalendarev:OM-25.01.2024 splitStatements:true endDelimiter:;
create table counterparties
(
    id              uuid not null primary key,
    full_name       varchar(255),
    name            varchar(75),
    inn             varchar(20),
    email           varchar(20),
    phone           varchar(75)
);

create table contracts
(
    contract_id                        uuid not null primary key,
    contract_number           varchar(70) not null,
    contract_date             date,
    start_date                date,
    end_date                  date,
    counterparty_id           uuid references counterparties (id),
    status                    varchar(20),
    notes                     varchar (255)
);

create table orders
(
  order_id              uuid not null primary key,
  order_number          varchar(255),
  order_name            varchar(255),
  bill_number           varchar(75),
  current_sum           numeric(10, 2),
  total_price           numeric(10, 2),
  is_vat_include        boolean,
  vat                   numeric(10, 2),
  debt_sum              numeric(10, 2),
  order_state           varchar(75),
  master_status         varchar(75),
  completion_date       timestamp without time zone,
  is_government_order   boolean not null default false,
  created_date          timestamp without time zone not null,
  modified_date         timestamp without time zone not null,
  author                varchar(75),
  is_deleted            boolean not null default false,
  counterparty_id       uuid references counterparties (id),
  contract_id           uuid references contracts (contract_id)
);

create table materials
(
    id                  uuid not null primary key,
    material_type       varchar(255)
);

create table products
(
    product_id                uuid not null primary key,
    product_name              varchar(255)
);

create table order_items
(
    id                      uuid not null primary key,
    quantity                int not null default 0,
    quantity_shipped        int not null default 0,
    price_per_product       numeric(10, 2),
    total_price             numeric(10, 2),
    current_sum             numeric(10, 2),
    is_vat_include          boolean,
    vat                     numeric(10, 2),
    product_type            varchar(20),
    is_program_written      boolean not null default false,
    machine_id              uuid,
    completion_date         timestamp without time zone,
    preparation_State       varchar(20),
    material_id             uuid references materials (id),
    order_id                uuid not null references orders (order_id),
    product_id              uuid references products (product_id)
);

create table cnc_machines
(
    id                  uuid not null primary key,
    machine_type        varchar(75),
    name                varchar(255),
    is_occupied         boolean not null default false,
    order_id            uuid references orders (order_id),
    order_item_id       uuid references order_items (id)
);

create table payments
(
    payment_id              uuid not null primary key,
    counterparty_id         uuid not null references counterparties (id),
    order_id                uuid not null references orders (order_id),
    payment_sum             numeric(20, 2),
    payment_date            timestamp without time zone,
    modified_date           timestamp without time zone
);

create table tasks
(
    id                      uuid not null primary key,
    description             varchar(255),
    is_completed            boolean not null default false,
    order_id                uuid references orders (order_id),
    created_date            timestamp without time zone,
    completed_date          timestamp with time zone,
    modified_date           timestamp without time zone
);

create table om_users
(
    id                      uuid not null primary key,
    first_name              varchar (50) not null,
    last_name               varchar (50) not null,
    patronymic              varchar (50),
    birthday                timestamp,
    email                   varchar (50) not null unique,
    phone                   varchar (20),
    password                varchar (255) not null,
    is_block                boolean not null default false,
    is_deleted              boolean not null default false
);

create table om_roles
(
    id                      uuid not null primary key,
    role                    varchar (50) not null,
    description             varchar (70)
);

create table om_user_role
(
  user_id uuid,
  role_id uuid,
  primary key (user_id, role_id),
  foreign key (user_id) references om_users (id),
  foreign key (role_id) references om_roles (id)
);