# Online Book Store

A Spring Boot REST API for an online bookstore with shopping cart functionality and simulated payment processing.

## Features

- **Book Management**: 
  - Search by title, author, genre, or year
  - Pre-loaded with sample books across different genres
- **Shopping Cart Operations**: 
  - Add/remove items
  - View cart contents
  - Clear cart
- **Order Processing**:
  - Create orders from cart items
  - Multiple payment methods (WEB, USSD, TRANSFER)
  - Order status tracking
- **User Management**:
  - Basic user operations
  - Order history

## Technical Stack

- Java 17
- Spring Boot 3.2.3
- Spring Data JPA
- H2 Database
- JUnit 5 & Mockito
- Maven
- Lombok

## API Endpoints

### Books
- `GET /api/books/search` - Search books with parameters
- `GET /api/books/{id}` - Get book by ID

### Cart
- `POST /api/cart/{userId}/items` - Add to cart
- `GET /api/cart/{userId}/items` - View cart
- `DELETE /api/cart/{userId}/items/{itemId}` - Remove from cart
- `DELETE /api/cart/{userId}/items` - Clear cart

### Orders
- `POST /api/orders/{userId}` - Create order
- `GET /api/orders/{userId}` - Get user's orders
- `POST /api/orders/{orderId}/process-payment` - Process payment

### Users
- `POST /api/users` - Create user
- `GET /api/users/{id}` - Get user
- `GET /api/users` - Get all users
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

## Setup and Running

1. Clone the repository
2. Build: `mvn clean install`
3. Run: `mvn spring-boot:run`
4. Access: `http://localhost:8082`

## Testing

The application includes comprehensive tests for both services and controllers. Run tests with: 
### Test Coverage
- Service layer tests for business logic
- Controller layer tests for API endpoints
- Integration tests for data flow

## Data Models

- **Book**: title, author, genre, price, etc.
- **User**: username, email
- **CartItem**: book, user, quantity
- **Order**: user, items, status, payment method
- **OrderItem**: book, quantity, price at purchase

## Future Improvements

1. Authentication & Authorization
2. Real payment gateway integration
3. Email notifications
4. Frontend application
5. API documentation
