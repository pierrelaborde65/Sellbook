# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table product (
  id_product                    bigint not null,
  id_seller                     varchar(255),
  name_product                  varchar(255),
  description_product           varchar(255),
  price_seller                  float,
  quantity_stock                integer,
  image_product                 varchar(255),
  constraint pk_product primary key (id_product)
);
create sequence product_seq;

create table user_sellbook (
  id                            bigint not null,
  name                          varchar(255),
  email                         varchar(255),
  number_address                integer,
  street_address                varchar(255),
  city_address                  varchar(255),
  post_code_address             integer,
  phone_number                  varchar(255),
  password                      varchar(255),
  siret                         varchar(255),
  description_seller            varchar(255),
  status_user                   varchar(255),
  token                         varchar(255),
  constraint uq_user_sellbook_email unique (email),
  constraint pk_user_sellbook primary key (id)
);
create sequence User_Sellbook_seq;


# --- !Downs

drop table if exists product;
drop sequence if exists product_seq;

drop table if exists user_sellbook;
drop sequence if exists User_Sellbook_seq;

