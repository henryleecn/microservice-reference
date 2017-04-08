# Microservices Reference - Auction Web-App

A Spring MVC app that demonstrates the consumption of microservices, while applying various application patterns.

### Getting Started

This project is a part of the Microservices referece group and needs at least one instance of each of the other services, namely, cloud config server, eureka server, sales microservice, bid microservice, user microservices, saleswithbid microservice, zuul-gateway.

The auction application consumes the microservices through the Zuul gateway.

It is configured(bootstrap.yml) to load its configuration from a Spring config server instance at http://your-server-host:8888. 
The application.yml allows configuring the version of services used by this application. 
It also has a URL pointing to a Node service that is used for bid submissions while allowing broadcast to other users.

### Prerequisites

This project uses Maven as the dependency management tool. The Java source level is set to JDK 8.

```
Maven 3
JDK 8
Local or remote instances of cloud config server, microservices, zuul and eureka servers.
```

### Installing

To setup, import into IDE as a maven project.

### Deployment

Run this project as a Spring Boot app, e.g. import into IDE and run
main method, or use Maven:

```
$ ./mvnw spring-boot:run
```

It will start up on port 8080 by default or as configured on the guthub config location.
This project uses war packaging. To generate the war file, use this command

```
$ ./mvnw package
```

### Security

Used Spring security. 
login.jsp is the form. LoginController is the controller handling authentication. 
UserServiceImpl implements the Spring UserDetailsService interface to handle authentication requests.