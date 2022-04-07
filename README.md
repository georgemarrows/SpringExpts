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
- shell / walking skeleton for requests (hardcoded data) + integration tests 
- CI/CD using Github Actions
- notes on patterns for financial applications (accounts / transactions)
- flesh out domain model + unit tests
- (maybe) simple UI
- (maybe) separate services

# Todos
- (Security) Spring leaks a lot of internal implementation information if you post data in the wrong format (eg no body) - how to turn this off? 
  RESOLVED - looks like it's a dev env thing. Doesn't leak info when running under integration tests.


# Tech notes

## Spring

### Getting started
https://spring.io/guides/gs/spring-boot/

### Spring & Gradle
https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/

### Logging
https://www.baeldung.com/spring-boot-logging


## REST / URL design

This is a thoughtful article on moving beyond simple CRUD for APIs: https://blog.palantir.com/rethinking-crud-for-rest-api-designs-a2a8287dc2af
