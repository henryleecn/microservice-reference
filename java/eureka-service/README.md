# Spring Cloud Eureka Server

This project executes Netflix Eureka server as a stand alone instance.

### Getting started
This project is a Spring boot application. By default it attempts to load central configuration from a Spring cloud config instance at http://your-server-host:8888 as configured in the bootstrap.yml file.
Please check the cloud config git location of the current configuration. If the config server is unmodified, the properties will load from https://github.com/saiyedzaidi/microservices-config/blob/master/eureka-server.yml

## Pre-requisites

A Config server instance having properties for Eureka server.
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

As per initial config, it will start up on port 10001 and the dashboard can be viewed at http://<hostname-or-ip>:10001


## Security

The configuration allows proving a username and password combination that is used to authenticate all access to Eureka. The same credentials need to be provided to all applications that need to register with or discover using Eureka.