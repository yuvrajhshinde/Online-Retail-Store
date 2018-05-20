## Summary ##

This project is implementation of online retail checkout counter which creates various product & generates final bill. RESTful services are used to implement this application.Tax applied on product depends upon the Category of various product.

1.	Category A - 10% tax is applied.
2.	Category B - 20% tax is applied.
3.	Category C -  0% tax is applied.

Test data for 3 Products and 1 bill are added during application startup. This application is developed using SpringBoot along with Spring Security & Spring Data/JPA.


## REST endpoints ##

User can add, update, modify & delete products using REST endpoints.

### Product ###

1. 	GET /products - View list of all product data.
2.  GET /products/{id} - View a specific product.
3.  POST /products - Creates a new product based on JSON request
4.	PUT /products/{id} - Updates product data based on JSON request.
5.	DELETE /products/{id} - Delete an existing product if it is not associated with a bill.


### Bill ###

1.	GET /bills - View all bill data.
2.	GET /bills/{id} - View bill of a particular id.
3.	POST ./bills - Creates a bill Id. User needs to use this bill Id while adding/ removing products
4.	PUT /bills/{id} - Updates bill data. User can add or remove products to bill sending a JSON request.
5.	DELETE /bills/{id} - Delete bill from the system.


## Run Application. ##

Pre-requisites – Make sure Java, Maven and Git are installed on machine.

Steps to build and run application locally:

1. 	Open command line.
2.	Create a new directory called "RetailCounterApp".
3.	Clone repository using following command -
     Git clone https://github.com/yuvrajhshinde/Online-Retail-Store
4.  Go to Online-Retail-Store folder.
5.  Build the executable jar using maven - mvn package
6.	Go to target folder - cd target.
7.	Run following command to start the server on port 8080.
    java -jar OnlineRetailStoreApp-0.0.1-SNAPSHOT.jar
8.	Access APIs using url => http://localhost:8080/swagger-ui.html
9.	Use yuvi as username & password for authentication.
This application uses in memory H2 database & data is lost after server restart.
