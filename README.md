Summary
This project is implementation of online retail checkout counter which creates various product & generate final bill. RESTful services are used to implement this application.Tax applied on product depends upon the Category of product.
1.	Category A - 10% tax is applied.
2.	Category B - 20% tax is applied.
3.	Category C- 0% tax is applied.
Test data for 3 Products and 1 bill are added during startup to browse. This application is developed using SpringBoot along with Spring Security & Spring Data/JPA.
REST endpoints
User can add, update, modify & delete products using REST endpoints.
Product
•	GET /products - fetches list of all product data
•	GET /products/{id} - fetch a specific product
•	POST /products - Creates a new product based on request JSON
•	PUT /products/{id} - Updates product data based on request JSON
•	DELETE /products/{id} - Delete an existing product if it is not associated with a bill.
Bill
•	GET /bills - fetches all bill data
•	GET /bills/{id} - fetches bill of a particular id
•	POST /bills - creates a bill Id. Client has to use this bill Id while adding and removing products
•	PUT /bills/{id} - Updates bill data. Client can add or remove products to bill sending a JSON request.
•	DELETE /bills/{id} - Delete bill from the system.
These REST end points are secured using basic authentication. User yuvi as username & password to login Swagger console.

Run Application.
Pre-requisites – Make sure Java, Maven and Git are installed on machine.
Steps to build and run application locally:
•	Open commandline.
•	Create a new directory called "onlineretailstore"
•	Clone repository using following command
Git clone https://github.com/yuvrajhshinde/Online-Retail-Store
•	Build the executable jar using maven - mvn package
•	Go to target folder - cd target
•	Run following command to start the server on port 8080
 java -jar OnlineRetailCounterApp-0.0.1-SNAPSHOT.jar
•	Access APIs using url => http://localhost:8080/swagger-ui.html
•	Use yuvi as username & password for authentication.
This application uses in memory H2 database & data is lost after server restart.

