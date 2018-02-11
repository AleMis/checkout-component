create database online_shop;

create table products(product_id int not null auto_increment, name varchar(255), individual_number varchar(20), description varchar(255), manufacturer varchar(50), base_price DECIMAL(12,2), primary key (product_id));
create table discounts(discount_id int not null auto_increment, product_individual_number varchar(20), description varchar(255), units int, special_price DECIMAL(12,2), since date, until date, primary key (discount_id));
create table baskets(basket_id int not null auto_increment, user_id int, is_open boolean, basket_value DECIMAL(12,2), primary key (basket_id));
create table basket_products(basket_product_id int not null auto_increment, product_individual_number varchar(20), units int, price DECIMAL(12,2), basket_id int, primary key (basket_product_id), foreign key (basket_id) references baskets(basket_id));
create table warehouse(warehouse_id int not null auto_increment, product_id int not null, product_individual_number varchar(20), units int, primary key (warehouse_id), foreign key (product_id) references products(product_id));


insert into products(name, individual_number, description, manufacturer, base_price) values('Sony Xperia XL', 'SONYXL', '8GB, 21mpx', 'Sony', '1500.00');
insert into products(name, individual_number, description, manufacturer, base_price) values('Samsung Galaxy S8', 'SAMSUNGS8', '5,8 cala, 21mpx', 'Samsung', '2000.00');
insert into products(name, individual_number, description, manufacturer, base_price) values('Huawei Mate 10', 'MATE10', '5,9 cala, 16+2mpx', 'Huawei', '2100.00');

insert into discounts(product_individual_number, description, units, special_price, since, until) values('SONYXL', 'February discount', '3', '4000.00', '2018-02-01', '2018-02-28');
insert into discounts(product_individual_number, description, units, special_price, since, until) values('MATE10', 'March discount', '2', '3500.00', '2018-03-01', '2018-03-21');

insert into warehouse(product_id, product_individual_number, units) values('1', 'SONYXL', '5');
insert into warehouse(product_id, product_individual_number, units) values('2', 'SAMSUNGS8', '4');
insert into warehouse(product_id, product_individual_number, units) values('3', 'MATE10', '1');