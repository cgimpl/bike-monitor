# bike-monitor

---
> A Spring Boot application that provides a REST API to determine bicycle traffic volume at a specific location and time

Users can fetch data from the external api, store it in the configured database, and query the data.
Additionally, users can create, update and delete locations via the provided endpoints.
The repository also includes example tests for controller and service classes.
This backend utilizes the publicly available dataset from [govdata.de](https://www.govdata.de/web/guest/suchen/-/details/radmonitore767ea),
which tracks bicycle counts at various locations every 15 minutes.

## Getting Started

You can interact with the backend by using the provided `postman_collection.json` located in the root directory.
You may download Postman [here](https://www.postman.com/downloads/).
This collection includes all the necessary requests to test the API.

At the beginning, send a POST request to `/api/fetch` to retrieve data from the external API and store in the database.
You may send the request multiple times, as only new data will be stored in the database.

## Technology Stack

- Java 17 with Spring Boot v3.3.3
  - Spring Web
  - Spring Data JPA
  - Lombok
  - Postgres Driver
  - Docker Compose Support
  - ModelMapper
- Postgres Database
  - Managed using Docker Compose (running on port `5433`)
  - The `docker-compose.yml` file does not include a volume, ensuring the data is volatile and reset with each deployment
  - The `init.sql` file hasn't been used; instead, the database schema is generated by Hibernate, as per the author's decision