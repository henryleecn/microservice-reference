# Spring Cloud Zuul Gateway

This project executes Netflix Zuul server as a stand alone instance.

### Getting started
This project is a Spring boot application. It is configured```bootstrap.yml``` to load its configuration from a Spring config server instance at ```http://your-server-host:8888```. 
The configuration dictates the service routes and how should Zuul handle them. 
This application employs Circuit breaker pattern using Hystrix while invoking the remote services. Hystrix allows for a fallback to be invoked when circuit is opened. Hystrix publishes monitoring data stream ```http://your-server-host:8085/hystrix.stream``` and also provides a decent dashboard ```http://your-server-host:8085/hystrix.dashboard``` that can visualise the stream.
It also uses a client side load balancer Ribbon to determine which instance to use among multiple instances of the same service.

## Pre-requisites

A Config server 
At least one Eureka server instance.
Maven and JDK8 for building and executing the application
While they are not need for starting up the Zuul gateway, the Microservices are needed to test the configured routes.

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

As per config, it will start up on port 8085. The Actuator endpoints allow for viewing the server details at the same port. The status of the server can be looked up from the Eureka dashboard.
