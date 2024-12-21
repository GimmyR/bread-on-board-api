# Spring Boot Starter

Look at the [Spring Boot Documentation](https://docs.spring.io/spring-boot/index.html) to learn more

## Prerequisites

* **Java** version **21.0.5**
* **Maven** version **3.9.9**
* **Oracle XE** version **21c 1.0.2**

## Configuration

Set up server port, log level, database and more configs in `application.properties`.

Find database schema in `schema.sql`.

## Build project

```bash
mvn clean install
```

## Deployment

Move `bread-on-board-0.0.1-SNAPSHOT.jar` in **target** directory to deployment directory and run this command in this directory :

```bash
java -jar bread-on-board-0.0.1-SNAPSHOT.jar
```

Find server on `http://localhost:[server-port]`.