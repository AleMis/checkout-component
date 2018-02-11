# Checkout Component #

Assumption of this application is that is is a backend tool used by an on-line shop, where customer is able to:
 * Open new empty basket – the basket is used to store items, 
 * Scan items – customer can select a product of one type and input it’s quantity, 
 * Close basket at any time and return the total price of a number of items.
 
Besides above functionalities user of the application is able to:
 * Add new products to database,
 * Create discount for products  
 
### Build With ###

 * Java - main programming language
 * Spring Boot, Spring MVC, Spring WEB, Hibernate - frameworks
 * Gradle - dependency management
 * MySQL - databaseg

### Prerequesites ###

* Gradle
* MySQL

### How to run ###

1. Download project from github.
2. Find sql file 'db_scheme.sql'. File contains sql queries which should be use do create database scheme.
3. Create database in mysql using 'create database online_shop' query.
4. Run all queries from 'db_scheme_sql' which create tables and sample data.  
5. Update 'spring.datasource.username' and 'spring.datasource.password' in application.properties file according to your database settings. 
6. Build project using 'gradlew build' command. 
7. Run the application. You can test application using eg. Postman.

 ### Main assumption and how application works ###
 
 1. Application has controller (ProductController) which gives opportunity to add and remove products which are available in the shop. 
    It doesn't mean that this product is in shop warehouse, but it means that shop sells this product.
    To use other functionalities of the application eg. add discount or add product to customer basket, this product should be first added to 'products' table.
    'products' table is responsible for keeping information about products (not units available to sell/buy).   
 2. Application has controller (DiscountController) which gives opportunity to add and remove discounts for products to/from 'discounts' table.
    Discounts should be provided as special price which client could receive for buying one or many units of some products 
    (eg. special price = 50; units = 3, it means that we can buy 3 units for 50)
 3. BasketController is the main controller of the application. BasketController is responsible for open new basket for the client, adding new product to the basket, and close product to the basket. 
    Before we add new product to the basket we have to open new basket for the customer. To open new basket of the parameter which should be provided to the method is user id. 
    Because application is only one microservice there is assumption that other microservice provides opportunity to get user id.  
    After each addition of the product to the basket, controller return information about whether new product was added to the basket (product could no be add to the basket because eg. there is no units available in the warehouse) as well as information about current value of the basket. 
    When customer will finish adding products to the basket, close basket method should be invoke to close customer current basket.
    Assumption of the application is that, customer could have only one open basket.   
 
       