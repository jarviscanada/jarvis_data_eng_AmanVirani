# Introduction
This app is used for CRUD process for a RDBMS database in a PSQL instance. The implementation uses JDBC to connect to the database, customer DAO(Data Access Object) to handle CRUD process, Dbeaver is used to generate ER Diagram, docker container to contain the PSQL instance.

# Implementaiton
## ER Diagram
![This is an ER Diagram Image](/assets/ER-Diagram.png)

## Design Patterns
- DAO(The Data Access Object) is a class that CRUD the customer object and DTO is a customer in this project.
DAO is a structural pattern that allows us to isolate the application/business layer from the persistence layer using an abstract API.
DAO is used as a mediator between the app and the postresql database.Using this design pattern gives us the ability to decouple business logic and database connection.
So the develop team can focus on what to do with DTO(Data transfer Object) i.e. customer, while DAO handles the CRUD interactions with database.

# Test
- Bash script was used to setup/start docker container.
- SQL Files were used to setup the PostgreSQL database and fill up the tables.
- Tested the CRUD operation from app and compared it from the database.
