# What is this project?
Catch-up
- I've been away from the Java world for a while - get back into that

New learning
- I've never used Spring - first steps with this
- I've never worked on financial applications before - read up & apply where relevant

# How to run
Builds use Gradle.

To run the server
- In this directory, run `./gradlew bootRun`, making sure you're using a Java 17 JDK
- Go to http://localhost:8080/

To run tests
- `./gradlew test`

# Outline plan
- DONE shell / walking skeleton for requests (hardcoded data) + integration tests 
- DONE CI/CD using Github Actions
- notes on patterns for financial applications (accounts / transactions)
- flesh out domain model + unit/integration tests
- (maybe) simple UI
- (maybe) separate services
- (maybe) report coverage to Codecov

# Todos
- RESOLVED (Security) Spring leaks a lot of internal implementation information if you post data in the wrong format (eg no body) - how to turn this off? 
  - Looks like it's a dev env thing. Doesn't leak info when running under integration tests.


# Tech notes

## Spring

- Getting started - https://spring.io/guides/gs/spring-boot/
- Spring & Gradle - https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/
- Logging - https://www.baeldung.com/spring-boot-logging


## DDD & Spring

- [Event Storming and Spring with a Splash of DDD](https://spring.io/blog/2018/04/11/event-storming-and-spring-with-a-splash-of-ddd)
  Interesting end-to-end analysis from event storming stickies through to Java code
- [Organizing Layers Using Hexagonal Architecture, DDD, and Spring](https://www.baeldung.com/hexagonal-architecture-ddd-spring)
  Much lighter, more code-focussed example
- [Strategic Domain-Driven Design](https://dev.to/peholmst/strategic-domain-driven-design-3e87) Epic, book-length (and book quality writing from my initial impression). One to read later. 


## Financial systems

- [Maintaining balances transactionally](https://softwareengineering.stackexchange.com/questions/416489/what-is-the-best-way-to-model-transactional-system-with-a-need-to-read-holding-b)
- [Patterns for Accounting](https://martinfowler.com/eaaDev/AccountingNarrative.html)
- [Accounting Patterns](https://martinfowler.com/apsupp/accounting.pdf)


## REST / URL design

This is a thoughtful article on moving beyond simple CRUD for APIs: https://blog.palantir.com/rethinking-crud-for-rest-api-designs-a2a8287dc2af

## Testing microservices

Don't use the test pyramid?
https://engineering.atspotify.com/2018/01/testing-of-microservices/

## Java
- Record syntax help cut down on noise for read-only / value classes
- AssertJ exists. Not sure what advantages are over hamcrest (apart from having a more obvious name!)