# Online Bookstore Backend

This is a Spring Boot-based backend application for an online bookstore that manages inventory, book pricing, and customer loyalty points.

## Features

- Book inventory management (add, modify, remove books)
- Dynamic pricing based on book types:
  - New Releases: 100% of base price
  - Regular Books: 100% of base price (10% discount for 3+ books)
  - Old Editions: 80% of base price (additional 5% discount for 3+ books)
- Customer loyalty points system:
  - 1 point per book purchased
  - Free regular/old edition book at 10 points
  - Points reset after redeeming

## Technical Stack

- Java 17
- Spring Boot 3.2.3
- Spring Data JPA
- H2 Database (in-memory)
- Maven
- Lombok

## Project Structure

The project follows a clean architecture approach with the following layers:
- Controllers (REST API endpoints)
- Services (Business logic)
- Repositories (Data access)
- Models (Domain entities and DTOs)

## How to Run

1. Ensure you have Java 17 and Maven installed
2. Clone the repository
3. Navigate to the project directory
4. Run: 

```bash
mvn spring-boot:run
```

5. The application will start on `http://localhost:8080`

## How to Run Tests

To run the unit tests for the application, use the following Maven command:

```bash
mvn test
```

This command will execute all the tests in the `src/test/java` directory and provide a summary of the test results.

Ensure that you have Maven installed and configured properly on your system before running the tests.

## API Endpoints

For detailed API examples and curl commands, see [EXAMPLE.md](EXAMPLE.md)

## Design Decisions

1. **Database Choice**: H2 in-memory database for simplicity and easy setup
2. **API Design**: RESTful API following best practices and HTTP standards
3. **Book Type Strategy**: Used Strategy pattern for flexible pricing calculations
4. **Validation**: Implemented input validation using Spring Validation
5. **Testing**: Unit tests for core business logic

## Future Improvements

1. Add authentication and authorization
2. Implement caching for frequently accessed data
3. Add more comprehensive error handling
4. Implement pagination for large datasets
5. Add API documentation using Swagger/OpenAPI
6. Add integration tests 