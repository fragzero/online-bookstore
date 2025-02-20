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
4. Run: `mvn spring-boot:run`
5. The application will start on `http://localhost:8080`

## API Endpoints

For detailed API examples and curl commands, see [EXAMPLE.md](EXAMPLE.md)

### Books
- GET `/api/books` - List all available books
- POST `/api/books` - Add a new book
- PUT `/api/books/{id}` - Update a book
- DELETE `/api/books/{id}` - Remove a book

### Purchases
- POST `/api/purchases` - Purchase books
- GET `/api/purchases/{customerId}` - Get purchase history

### Loyalty Points
- GET `/api/loyalty/{customerId}` - Get customer loyalty points
- POST `/api/loyalty/{customerId}/redeem` - Redeem loyalty points

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