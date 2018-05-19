## Summary ##

This project is implementation of online retail checkout counter which creates various product & generate final bill. RESTful services are used to implement this application.Tax applied on product depends upon the Category of product.

1.	Category A - 10% tax is applied.
2.	Category B - 20% tax is applied.
3.	Category C- 0% tax is applied.

Test data for 3 Products and 1 bill are added during startup to browse. This application is developed using SpringBoot along with Spring Security & Spring Data/JPA.


## REST endpoints ##

User can add, update, modify & delete products using REST endpoints.

### Product ###

1. 	GET /products - fetches list of all product data
2.  GET /products/{id} - fetch a specific product
3.  POST /products - Creates a new product based on request JSON
4.	PUT /products/{id} - Updates product data based on request JSON
5.	DELETE /products/{id} - Delete an existing product if it is not associated with a bill.


### Bill ###

1.	GET /bills - fetches all bill data
2.	GET /bills/{id} - fetches bill of a particular id
3.	POST ./bills - creates a bill Id. Client has to use this bill Id while adding and removing products
4.	PUT /bills/{id} - Updates bill data. Client can add or remove products to bill sending a JSON request.
5.	DELETE /bills/{id} - Delete bill from the system.


## Run Application. ##

Pre-requisites – Make sure Java, Maven and Git are installed on machine.

Steps to build and run application locally:

1. 	Open commandline.
2.	Create a new directory called "onlineretailstore"
3.	Clone repository using following command
Git clone https://github.com/yuvrajhshinde/Online-Retail-Store
4.	Build the executable jar using maven - mvn package
5.	Go to target folder - cd target
6.	Run following command to start the server on port 8080
 java -jar OnlineRetailCounterApp-0.0.1-SNAPSHOT.jar
7.	Access APIs using url => http://localhost:8080/swagger-ui.html
8.	Use yuvi as username & password for authentication.
This application uses in memory H2 database & data is lost after server restart.
