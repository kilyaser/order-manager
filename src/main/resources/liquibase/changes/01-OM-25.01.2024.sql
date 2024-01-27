create table orders
(
  order_id              uuid not null primary key,
  order_number          varchar(255),
  order_name            varchar(255),
  current_sum           decimal(10, 2),
  debt_sum              decimal(10, 2),
  master_status         varchar(75),
  completion_date       timestamp without time zone,
  is_government_order   boolean not null default false,
  created_date          timestamp without time zone not null,
  modified_date         timestamp without time zone not null
);

create table counterparties
(
    id              uuid not null primary key,
    full_name       varchar(255),
    name            varchar(75),
    inn             varchar(20),
    email           varchar(20),
    phone           varchar(75)
);