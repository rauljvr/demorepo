# Demo project using Spring Boot for consuming a Rest API through a custom RestTemplate Client

I developed a Rest API which provides an interface to be able to call the services of the fake Rest API offered by jsonplaceholder.

								WHAT IS NEEDED?

To develope this project I worked with a bunch of frameworks, patterns and tools such as:

Spring Boot 2.3.4, Eclipse 2019-12, Spring Tools 3, Maven 3.6.2, Java 1.8, H2 in-memory DB, Swagger 2, Spring JPA/Hibernate, Lombok, Builder, SLF4J, JUnit/Mockito, Jackson.

								HOW TO RUN THE APPLICATION?

To run the application just execute the following commands using any Command prompt, as long as the Maven environment variables have been configured in advanced.

> mvn clean install

> mvn spring-boot:run

Or by importing the project in your favourite IDE and start the service.

Then you can check the records in the H2 console: http://localhost:8080/h2

- Driver Class: org.h2.Driver

- JDBC URL: jdbc:h2:mem:apirestdb

- User Name: root

									ENDPOINTS

Here the examples of the endpoints for testing the RESTful API for the resources Album and Photo. You can try these endpoints in any testing tool for Web services like Postman.
I also included Swagger configuracion to try them out throught it by going on: http://localhost:8080/swagger-ui.html

The endpoints are:

Method GET:

1. Getting an album: http://localhost:8080/restApiService/albums/1

2. Getting all albums: http://localhost:8080/restApiService/albums

3. Getting a photo: http://localhost:8080/restApiService/photos/1

Method POST:

4. Creating an album: http://localhost:8080/restApiService/albums

Json body: 
{
    "userId": 1,
    "title": "new title!"
}

5. Creating a photo: http://localhost:8080/restApiService/photos

Json body:
{
    "albumId": 1,
    "title": "favourite landscape",
    "url": "https://via.placeholder.com/601/90c902",
    "thumbnailUrl": "https://via.placeholder.com/100/90c902"
}

Method PUT:

6. Updating an album: http://localhost:8080/restApiService/albums/100

Json body: 
{
    "userId": 1,
    "title": "new title!"
}

7. Updating a photo: http://localhost:8080/restApiService/photos/10

Json body: 
{
    "albumId": 1,
    "title": "new title!",
    "url": "https://via.placeholder.com/601/90c902",
    "thumbnailUrl": "https://via.placeholder.com/100/90c902"
}

Method DELETE:

8. Deleting an album: http://localhost:8080/restApiService/albums/1

									SETTINGS

- By default I decided to load some data in the DB in advance, only for the tables 'User' and 'Company' in order to show the implementation of the repository layer.

- As required, the data of one the resources is storing in Json and XML files once is got from the external Rest API. I also persist the info in the H2 DB (Embedded). The path of this files can be configured in the YAML file (application.yml) which will depend on the OS that you are using.

	- placeholder.output.albumJsonFile
	- placeholder.output.albumXmlFile
