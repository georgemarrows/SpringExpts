# What is this project?
Catch-up
- I've been away from the Java world for a while - get back into that

New learning
- I've never used Spring - first steps with this
- I'm not familiar with DDD - let's understand some code patterns here
- I've never worked on financial applications before - read up & apply where relevant

# How to run
- Builds use Gradle.
- Code has only been tested on Java 17. It uses `var` (Java 10) and `record` (Java 14).
- CI is via Github Actions: https://github.com/georgemarrows/SpringExpts/actions

### To run the server
- `./gradlew bootRun`
- Server is at `localhost:8080`
- There is a built-in test contact with `customerId=a fixed id for testing`
- Endpoints are
  - POST /api/account
    expects application/json body in the form
    ```
    {
      customerId: XXX,
      initialCredit: 1000
    }
    ```
    Postman example:

    ![image](https://user-images.githubusercontent.com/5624098/162637876-0600f089-c5ba-4564-a5da-a8edba6edd95.png)


  - GET /api/account?customerId=???
    Postman example:

    ![image](https://user-images.githubusercontent.com/5624098/162637970-64910d86-ab0e-4aea-b50f-6152240ed1f4.png)  


### To run tests
- `./gradlew test`


# Outline plan
- DONE shell / walking skeleton for requests (hardcoded data) + integration tests 
- DONE CI/CD using Github Actions
- DONE notes on patterns for financial applications (accounts / transactions)
- DONE flesh out domain model + unit/integration tests
- (maybe) simple UI
- (maybe) separate services
- (maybe) report coverage to Codecov

# Challenges

## Retrospective
1. Learning (the beginnings of) Spring here was tricky. It's a big beast. Their getting started info is useful, but then there's a giant leap to their Javadoc, which is thin and not at all oriented around how to use it. Fortunately [Baeldung](https://www.baeldung.com/) does a good job of stepping in. StackOverflow is very helpful too.
2. I learnt even less of DDD, and definitely only at the "tactical" code level. ("Strategic"/"tactical" terminology from [this article](https://dev.to/peholmst/tactical-domain-driven-design-17dp).) I'm beginning to get a sense of "why DDD?" at the level of code, and it's obvious that the clean domain models could be very useful. But issues like aggregates are still a mystery.
   
   It's interesting to compare the mutable DDD domain model with the immutable model suggested in [Boundaries](https://www.destroyallsoftware.com/talks/boundaries) by Gary Bernhardt. Immutable objects can be a lot easier to understand. Putting aside mutability, the overall structure is similar, both being careful to separate the communications technology (database, other services) from the internal domain model.

   [This](https://lorenzo-dee.blogspot.com/2016/10/architectural-layers-and-modeling.html) is a useful corrective to getting DDD-obsessed. CRUD is the right solution for many subsystems ("bounded contexts").

3. Turning to the code, I'm not happy about how services & repositories are overly tangled, with eg `CustomerService` talking to `AccountRepository` as well as `CustomerRepository`. I doubtless need to understand the use of aggregates better.

4. Good to see a couple of shortcuts in modern Java:
   - Using `record` for easy de/serialising of JSON in controllers, or returning multiple results from a method
   - AssertJ's `extracting` makes some asserts much more readable

## Open issues
1. Code is probably insufficiently commented. I'm not a fan of large swathes of comments doc'ing the simple or obvious, and none of the code here is at all algorithmically complicated or contains hidden gotchas or important cross-references. Those are the kind of things I would document.

2. I've baked in `InMemoryAccountRepository`, which should be switchable for a persistent, database-backed repository. I haven't spent time yet figuring out how to use Spring DI to do that. There's likely to be a few knock-ons on services and perhaps the domain model too.
   - As a side effect of not integrating with a database or ORM, I'm not yet sure how to make `CustomerService.createAccount()` transactional. Pretty important!
  
3. I don't yet understand how DDD's aggregates would handle unbounded data like transactions for an account.

4. Money comes from nowhere! What account should be debited when the customer's new account is credited. This made me realise I don't know how banks track money flowing into their business. That's why `Transaction.dummyFromAccountId` exists.

5. Money should be a DDD [value object](https://dev.to/peholmst/tactical-domain-driven-design-17dp#value-objects)

6. Nothing on authentication or authorisation

## Resolved
- (Security) Spring leaks a lot of internal implementation information if you post data in the wrong format (eg no body) - how to turn this off? 
  - Looks like it's a dev env thing. Doesn't leak info when running under integration tests.


# Background reading

## Spring

- Getting started - https://spring.io/guides/gs/spring-boot/
- Spring & Gradle - https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/htmlsingle/
- Logging - https://www.baeldung.com/spring-boot-logging
- Dependency injection
    - https://stackoverflow.com/questions/34367316/spring-boot-autowired-does-not-work-classes-in-different-package?rq=1
    - https://stackoverflow.com/questions/24014919/understanding-spring-configuration-class


## DDD & Spring

- [Event Storming and Spring with a Splash of DDD](https://spring.io/blog/2018/04/11/event-storming-and-spring-with-a-splash-of-ddd)
  Interesting end-to-end analysis from event storming stickies through to Java code
- [Organizing Layers Using Hexagonal Architecture, DDD, and Spring](https://www.baeldung.com/hexagonal-architecture-ddd-spring)
  Much lighter, more code-focussed example
- [Strategic Domain-Driven Design](https://dev.to/peholmst/strategic-domain-driven-design-3e87)
  Book-length (and book quality writing from my initial impression). One to read later. 
- [Architectural Layers and Modeling Domain Logic](https://lorenzo-dee.blogspot.com/2016/10/architectural-layers-and-modeling.html)
  Points out that you don't always need a domain model. Some bounded contexts genuinely are just CRUD and anything more than controller + repository is over-engineering.
- [Domain Driven Design Quickly](https://www.infoq.com/minibooks/domain-driven-design-quickly/)
  Not downloaded yet.

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
New things in Java land since I was last here...
- `var` declarations
- Record syntax help cut down on noise for read-only / value classes
- AssertJ exists. Not sure what advantages are over hamcrest (apart from having a more obvious name!)
- java.time replaces JodaTime. There's also https://www.threeten.org/threeten-extra/
- """ multi-line strings have to start with a new line :-(
