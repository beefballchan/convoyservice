## Answers for Questions

1. How will it be tested? 
Unit tests are included.

1. How will it handle real world input?
Spring Boot has included basic http response e.g. 405 Method Not Allowed, 404 Page Not Found, and 400 Bad Request.

1. How does it behave under concurrent load?
Currently, it is using the default implicit optimistic lock which mean race condition can still exist. A proper write lock needs to be chosen to avoid race condition especially for accepting offers.

1. Can the code be easily extended to become v1 of the convoy API?
Yes, this can be easily extended to become v1 
* by fixing the concurrent use case. The framework has built-in annotation to specify LockTypeMode.
* by writing test for concurrent use cases
* by creating environment specific config
* by setting environment specific db


## Answers for Additional Questions
Please write a paragraph or two for each of the following questions.

1. What persistence solution did you choose and why?
Spring boot H2 database is chosen for this exercise because the data itself is relational and so it makes sense to pick a RDBMS.
H2 is chosen because spring boot support H2 in memory database for faster set up for sandbox development.

1. What are some other ways you might score a driver?
A driver can rank higher depends on how fast and how often they responds to offer proposed to them and also the ratio of rejection/acceptance of offers.

1. What do you think are the best features to implement? When and why?
It is very hard to say what are the best features. The best features should driven by the business need based on where the current product stands.
From what I understand of the business model, convoy can give a lower price to shippers and a higher pay to drivers compared to the traditional brokers due to their deep profit margin. On top of that, convoy can also offer convenience to the shippers and drivers. Convoy can offer them shipment management module where they can see all the statistics and the past and active shipments, and to create new orders and new drivers. The module can also push notifications of upcoming shipments etc. 


1. What would you add/change for a real-world v1 of this system?
For a real-world service to work, the system will need information about the schedule and the routes. The system needs to make sure drivers won't accept offers with overlapping schedules. Base on the route info, driver can continue to be offered additional shipment of the same route for the remaining capacity. 

## Instruction to start the project

#### Build the project
> mvn clean package

#### Run the project
> mvn clean spring-boot:run

#### Database Management Console
database console - http://localhost:8080/console


