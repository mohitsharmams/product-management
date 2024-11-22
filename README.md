Product-management application:
------------------------------
This is a Spring Boot-based RESTful API that allows CRUD operations on a Product resource. Application also implements authentication, input validation, error handling, and uses a MySQL database for persistence.

Features:
--------
- Authentication using JWT (JSON Web Tokens)
- CRUD operations for Product resource
- Input validation
- Global exception handling
- API documentation with Swagger UI

Technologies Used:
-----------------
- Spring Boot 3.3.6
- Spring Data JPA (Hibernate)
- MySQL (Relational Database)
- Spring Security (Authentication)
- JWT (JSON Web Tokens)
- Swagger/OpenAPI (API Documentation)
- Bean Validation for input validation
- Liquibase for database changelogs migrations

Prerequisites:
-------------
- Java 17 installed
- Maven
- MySQL installed and running
  - create database for the application (productdb)

Project Setup
-------------
1. Clone the Repository
   - Clone this project from GitHub:
     git clone https://github.com/mohitsharmams/product-management.git

2. Configure Database:
   - Set up MySQL/PostgreSQL database connection in application.yml (located in src/main/resources):
   - Set the following properties:
     - datasource.url: jdbc:mysql://localhost:3306/productdb
     - username: root  // your db user-name
     - password: P@55w0Rd#01  // your db password

3. Build the Application: 
   - To build the application, you need to execute below command from terminal
     - mvn clean install

4. Run the Application:
   - To run the Spring Boot application, use below command
     - mvn spring-boot:run
   - The application will start on http://localhost:8080 by default.

Secrets
-------
- For simplicity I have placed all the secrets in the application.yml. For production ready code we can choose any of the secret places such as aws-secrets
- Application requires credential to authenticate the user:

- credentials:
  username: user
  password: password

- credentials validation:
  username: should be in the range of 4 to 20 characters
  password: should be more than or equal to 8 characters


API Endpoints
-------------
1. Open the swagger-ui with url: http://localhost:8080/swagger-ui/index.html

2. Authentication:
   - First you need to hit the login Api: "/auth/login" (Use credentials as stated above in "Secrets" section)
   - This Api returns LoginResponse with fields: "accessToken" & "username"
   - Copy the value of field "accessToken" (Without the double quotes (""))
   - Click on "Authorize" section (Top right in swagger-ui)
   - Paste the copied token value (DO NOT use "Bearer" along with the token value, because its configured with this prefix in application for swagger)
   - Now you are good to go to hit rest of the end points ("Product" Crud operations)  

3. "Product" CRUD Operations

   - GET /products
     Retrieves all products list.

   - GET /products/{id}
     Retrieves a product by ID.

   - POST /products
     Creates a new product.

   - PUT /products/{id}
     Updates an existing product by ID.

   - DELETE /products/{id}
     Deletes a product by ID.


Unit and Integration Test Cases
-------------------------------
Tests cases ensure the correctness and reliability of the application.

1. Unit Tests: Application covers Unit tests cases focusing individual components of the application (such as the service layer) in isolation which ensure that each business logic component works as expected.

2. Running the test cases: use below mvn cammand to run the test cases
   - mvn test