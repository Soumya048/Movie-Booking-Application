# Movie-Booking-Application

REST API for an Online Movie Booking Application. Users can book movie tickets to watch movies on any theatres. This API performs all the fundamental CRUD operations. There are validations for input data. and usersession uuid to access the API.

## Badges

![Badge](https://visitor-counter-badge.vercel.app/api/Soumya048/Movie-Booking-Application/)

## Tech Stack:
- Java
- Spring Framework
- Spring Boot
- Spring Data JPA
- Hibernate
- Spring Validation
- MySQL
- Lambok
- Swagger Ui

## Modules
- Login Module
- Admin Module
- Customer Module
- Movie and Theatre Module
- Movie Booking Module

## ER Diagram:

![Untitled Diagram drawio (3)](https://user-images.githubusercontent.com/91946820/202412928-3a08a350-86e8-400b-b0da-4d2f8a83dbc6.png)


## Installation and Run

```
#changing the server port
server.port=8088

#db specific properties
spring.datasource.url=jdbc:mysql://localhost:3306/moviedb
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=root

#ORM s/w specific properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

#No handler found
spring.mvc.throw-exception-if-no-handler-found=true
spring.web.resources.add-mappings=false

#swagger-ui
spring.mvc.pathmatch.matching-strategy = ANT_PATH_MATCHER

#using Gmail SMTP server
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=<email>
spring.mail.password=<password>
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

## API Root and Endpoint

```
https://localhost:8088/
```

#### for swagger
```
http://localhost:8088/swagger-ui/
```
