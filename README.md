# Microservices Reference

This project is a reference application to be used as a quick start for building microservice using Spring cloud stack.
It uses Couchbase server and elasticsearch and couchbase-elasticsearch-transport plugin too.
The project consists of these sub-projects

* [Bid Microservice](https://github.com/saiyedzaidi/microservice-reference/tree/master/java/bidservice)
* [Sale Microservice](https://github.com/saiyedzaidi/microservice-reference/tree/master/java/saleservice)
* [User Microservice](https://github.com/saiyedzaidi/microservice-reference/tree/master/java/userservice)
* [Sales composite service](https://github.com/saiyedzaidi/microservice-reference/tree/master/java/zuul-gateway)
* [Config Server](https://github.com/saiyedzaidi/microservice-reference/tree/master/java/configserver)
* [Eureka Server](https://github.com/saiyedzaidi/microservice-reference/tree/master/java/eureka-service)
* [Zuul Gateway](https://github.com/saiyedzaidi/microservice-reference/tree/master/java/zuul-gateway)
* [Auction web app](https://github.com/saiyedzaidi/microservice-reference/tree/master/node)
* [NodeJS based placebid service](https://github.com/saiyedzaidi/microservice-reference/tree/master/node)

### Getting Started

The sub-projects implement various components needed for managing, monitoring, routing, registering and discovering microservices. There needs to be at least 1 running instance of each of the sub projects for this project to work.
All the Java projects in this application are implemented using Spring boot with Maven. They could be imported to any IDE that supports Maven.

### Prerequisites

#### Couchbase server enterprise 4.5 or higher

```
A bucket to be created on the couchbase server with the name microservices
```

#### Elasticsearch server 5.2.0

Couchbase Transport Plugin for Elasticsearch ```https://github.com/couchbaselabs/elasticsearch-transport-couchbase``` to be installed into Elasticsearch.
Elasticsearch index to be created using the settings from file ```elasticsearch-index-settings.txt```
Configure data sync from Couchbase to elasticsearch using the steps from section ```Starting Data Transfer``` in the above linked page.

#### Github repository for central configuration
```
A yml file corresponding to each project is saved on a Github repository to serve as a central co configuration source.
```

#### JDK 8

### Installing

To install, projects have to be started in this order: Configserver, Eureka Server, Microservices, Zuul gateway, Composite services, AuctionApp, Node application.
All Java projects use the Config server to lookup their config, hence the config server needs to be started first.
Configserver's actual location must be updated into the cloud config guthub location for other projects to lookup.
Individual projects documentation should be followed for their specific steps.

### Deployment

Individual projects can be deployed locally or remotely in a totally independent fashion. 
Every project supports creation of a deployable jar or war file that can be executed on the target environment. To create the deployable use the command

```
.\mvnw package
```
The generated Jar file could be just copied over to target server and started with  the following command.
```
java -jar <filename>
```
The AuctionApp project generates a war file, that could be deployed to a servlet container. 