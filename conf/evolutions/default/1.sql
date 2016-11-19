# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table order_sellbook (
  id_order                      bigint not null,
  id_user                       bigint,
  id_seller                     bigint,
  id_product                    bigint,
  date_order                    varchar(255),
  state_order                   varchar(255),
  quantity_order                integer,
  price_order                   float,
  constraint pk_order_sellbook primary key (id_order)
);
create sequence Order_Sellbook_seq;

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

create table product_in_shopping_cart (
  id                            bigint not null,
  quantity                      integer,
  reference_user_id             bigint,
  reference_product_id_product  bigint,
  constraint pk_product_in_shopping_cart primary key (id)
);
create sequence product_in_shopping_cart_seq;

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

alter table product_in_shopping_cart add constraint fk_product_in_shopping_cart_reference_user_id foreign key (reference_user_id) references user_sellbook (id) on delete restrict on update restrict;
create index ix_product_in_shopping_cart_reference_user_id on product_in_shopping_cart (reference_user_id);

alter table product_in_shopping_cart add constraint fk_product_in_shopping_cart_reference_product_id_product foreign key (reference_product_id_product) references product (id_product) on delete restrict on update restrict;
create index ix_product_in_shopping_cart_reference_product_id_product on product_in_shopping_cart (reference_product_id_product);


# --- !Downs

alter table product_in_shopping_cart drop constraint if exists fk_product_in_shopping_cart_reference_user_id;
drop index if exists ix_product_in_shopping_cart_reference_user_id;

alter table product_in_shopping_cart drop constraint if exists fk_product_in_shopping_cart_reference_product_id_product;
drop index if exists ix_product_in_shopping_cart_reference_product_id_product;

drop table if exists order_sellbook;
drop sequence if exists Order_Sellbook_seq;

drop table if exists product;
drop sequence if exists product_seq;

drop table if exists product_in_shopping_cart;
drop sequence if exists product_in_shopping_cart_seq;

drop table if exists user_sellbook;
drop sequence if exists User_Sellbook_seq;

