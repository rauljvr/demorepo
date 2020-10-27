# Account Service RESTful API

The API will allow to create, access, find accounts and transfer money between them.

An account seen by a REST client has the following details:
- Name: String
- Currency: Currency
- Balance: Money
- Treasury: Boolean

The requirements are the following:
- Treasury property can be set only at creation time.
- Only treasury accounts (Treasury property) can have a negative balance.
- Non treasury accounts should block transfers if there is not enough balance.

									WHAT IS NEEDED?

To develope this project I worked with a bunch of libraries, frameworks and tools such as:

Spring Boot 2.3.4, Eclipse 2019-12, Spring Tools 3, Maven 3.6.2, Java 1.8, H2 in-memory DB, Swagger 2, Spring JPA/Hibernate, Lombok, ModelMapper, SLF4J, JUnit/Mockito/Hamcrest, Jackson.

								HOW TO RUN THE APPLICATION?

To run the application just execute the following commands using any Command prompt, as long as the Maven environment variables have been configured in advanced.

> mvn clean install

> mvn spring-boot:run

Or by importing the project in your favourite IDE and start the service.

Then you can check the records in the H2 console: http://localhost:8080/h2

- JDBC URL: jdbc:h2:mem:demodb

- User Name: root

									ENDPOINTS

Here the examples of the endpoints for testing the RESTful API. You can try these endpoints in any testing tool for Web services like Postman.

I also included Swagger configuracion to try them out throught it by going on: http://localhost:8080/swagger-ui.html

The endpoints are:

Method GET:

1. Get an account: http://localhost:8080/accountService/account/savings1

2. Get all accounts http://localhost:8080/accountService/accounts

3. Get a pageable list with all accounts http://localhost:8080/accountService/accountsPageable

Method PATCH:

4. Creating an account: for existing accounts it also creates or updates the currency and money entities (It's not allowed to modify the treasury attribute) 
http://localhost:8080/accountService/createAccount

Json body: { "name": "savings3", "currency": { "currencyCode": "CAN" }, "money": { "balance": 90.00 }, "treasury": false }

Method PUT:

5. Making a transfer between accounts by updating their balances
http://localhost:8080/accountService/transferMoneyAccount

Json body: { "fromAccount": "savings1", "toAccount": "savings2", "money": 200.00 }