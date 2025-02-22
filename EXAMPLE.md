# API Examples

This document provides examples of how to interact with the Online Bookstore API using curl commands.

## Book Operations

### Create Books

1. Create a New Release Book:
```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Tomorrow, and Tomorrow, and Tomorrow",
    "author": "Gabrielle Zevin",
    "isbn": "978-0593321201",
    "base_price": 29.99,
    "type": "NEW_RELEASE"
  }'
```

2. Create a Regular Book:
```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Project Hail Mary",
    "author": "Andy Weir",
    "isbn": "978-0593135204",
    "base_price": 19.99,
    "type": "REGULAR"
  }'
```

3. Create an Old Edition Book:
```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "To Kill a Mockingbird",
    "author": "Harper Lee",
    "isbn": "978-0446310789",
    "base_price": 15.99,
    "type": "OLD_EDITION"
  }'
```

### Other Book Operations

1. Get all books:
```bash
curl -X GET http://localhost:8080/api/books
```

2. Get a specific book:
```bash
curl -X GET http://localhost:8080/api/books/1
```

3. Update a book:
```bash
curl -X PUT http://localhost:8080/api/books/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Updated Title",
    "author": "Updated Author",
    "isbn": "978-0743273565",
    "base_price": 24.99,
    "type": "OLD_EDITION"
  }'
```

4. Delete a book:
```bash
curl -X DELETE http://localhost:8080/api/books/1
```

## Customer Operations

1. Create a customer:
```bash
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@example.com"
  }'
```

2. Get all customers:
```bash
curl -X GET http://localhost:8080/api/customers
```

3. Get a specific customer:
```bash
curl -X GET http://localhost:8080/api/customers/1
```

4. Update a customer:
```bash
curl -X PUT http://localhost:8080/api/customers/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.updated@example.com"
  }'
```

5. Delete a customer:
```bash
curl -X DELETE http://localhost:8080/api/customers/1
```

## Purchase Operations

1. Create a purchase without loyalty points:
```bash
curl -X POST "http://localhost:8080/api/purchases?customerId=1&bookIds=1,2,3&useLoyaltyPoints=false"
```

2. Create a purchase using loyalty points:
```bash
curl -X POST "http://localhost:8080/api/purchases?customerId=1&bookIds=1&useLoyaltyPoints=true"
```

3. Get purchase history for a customer:
```bash
curl -X GET http://localhost:8080/api/purchases/customer/1
```

## Testing Scenarios

### Testing Bundle Discounts

1. Create three regular books and purchase them together to test the 10% bundle discount:
```bash
# First, create a customer
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Customer",
    "email": "test@example.com"
  }'

# Then purchase three regular books
curl -X POST "http://localhost:8080/api/purchases?customerId=1&bookIds=2,2,2&useLoyaltyPoints=false"
```

### Testing Loyalty Points

1. Make multiple purchases to accumulate points:
```bash
# Make purchases to get 10 points
curl -X POST "http://localhost:8080/api/purchases?customerId=1&bookIds=1,2,3&useLoyaltyPoints=false"
curl -X POST "http://localhost:8080/api/purchases?customerId=1&bookIds=1,2,3&useLoyaltyPoints=false"
curl -X POST "http://localhost:8080/api/purchases?customerId=1&bookIds=1,2,3&useLoyaltyPoints=false"
curl -X POST "http://localhost:8080/api/purchases?customerId=1&bookIds=1&useLoyaltyPoints=false"

# Get loyalty points 
curl -X GET "http://localhost:8080/api/customers/loyalty/1"

# Then redeem points for a free book
curl -X POST "http://localhost:8080/api/purchases?customerId=1&bookIds=2&useLoyaltyPoints=true"
``` 