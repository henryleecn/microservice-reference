# Bid Microservice

Supports Bid features to demonstrate microservice architecture, employing service registration, health-check, central configuration etc.

### Getting started
This project is a Spring boot application. It is configured in ```bootstrap.yml``` to load its configuration from a Spring config server instance at ```http://your-server-host:8888```. 
The configuration provides the location of Eureka server this service should register itself with. This service gets a random port assigned. We may lookup the service port number from the Eureka registry.
Service properties ```application.yml``` allow setting of ```api.version``` which is applied to the URI of the services. 
The actuator endpoint URI remain the same irrespective of the versioning.

## Pre-requisites

A Config server and at least one Eureka server instance.
Couchbase database with a microservices bucket.
Maven and JDK8 for building and executing the application

### Installation
Run this project as a Spring Boot app, e.g. import into IDE and run
main method, or use Maven:

```
$ ./mvnw spring-boot:run
```

or

```
$ ./mvnw package
$ java -jar target/*.jar
```

The port of the server can be looked up from the Eureka dashboard.
