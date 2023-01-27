create table tb_role (id int8 generated by default as identity, role_name varchar(255) not null, primary key (id));
create table tb_user (id int8 generated by default as identity, email varchar(255) not null, name varchar(255), password varchar(255) not null, primary key (id));
create table tb_user_role (user_id int8 not null, role_id int8 not null, primary key (user_id, role_id));
alter table if exists tb_role add constraint UK_c9lijtmr0x68iu1vxftbu2u33 unique (role_name);
alter table if exists tb_user add constraint UK_4vih17mube9j7cqyjlfbcrk4m unique (email);
alter table if exists tb_user_role add constraint FKea2ootw6b6bb0xt3ptl28bymv foreign key (role_id) references tb_role;
alter table if exists tb_user_role add constraint FK7vn3h53d0tqdimm8cp45gc0kl foreign key (user_id) references tb_user;
