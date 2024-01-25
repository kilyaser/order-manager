create table orders
(
  order_id          uuid            not null primary key,
  order_number      varchar(255),
  order_name        varchar(255),
  current_sum       decimal(10, 2),
  master_status     varchar(75),
  completion_date   timestamp without time zone,
  created_date      timestamp without time zone not null,
  modified_date     timestamp without time zone not null
);