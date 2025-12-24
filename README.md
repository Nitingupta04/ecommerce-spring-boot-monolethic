# Monolithic E-Commerce Application

This is a monolithic Spring Boot application that consolidates all the functionality from the original microservices architecture into a single deployable unit.

## Architecture Overview

The application combines the following previously separate microservices:
- **Product Service**: Product and category management
- **User Service**: User registration and authentication
- **Order Service**: Order processing and management
- **Review Service**: Product reviews and ratings
- **Frontend Service**: Web UI using Thymeleaf

## Features

- **Product Management**: CRUD operations for products and categories
- **User Management**: User registration, login, and profile management
- **Order Processing**: Create and manage orders with order items
- **Review System**: Add reviews and ratings for products with automatic rating calculations
- **Web Interface**: Responsive web UI for browsing products and managing the store
- **REST API**: Complete REST API for all operations

## Technology Stack

- **Framework**: Spring Boot 2.7.18
- **Database**: H2 (in-memory for development)
- **ORM**: Spring Data JPA with Hibernate
- **Frontend**: Thymeleaf templates with Bootstrap styling
- **Build Tool**: Maven
- **Java Version**: Java 8

## Project Structure

```
src/
├── main/
│   ├── java/com/ecommerce/
│   │   ├── MonolithicEcommerceApplication.java  # Main application class
│   │   ├── controller/                          # REST and Web controllers
│   │   ├── entity/                             # JPA entities
│   │   ├── repository/                         # Data repositories
│   │   └── service/                            # Business logic services
│   └── resources/
│       ├── templates/                          # Thymeleaf templates
│       ├── static/                            # Static web resources
│       ├── application.properties             # Application configuration
│       └── data.sql                          # Sample data initialization
```

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven 3.6 or higher

### Running the Application

1. **Navigate to the project directory:**
   ```bash
   cd monolithic-ecommerce
   ```

2. **Build the application:**
   ```bash
   mvn clean compile
   ```

3. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application:**
   - Web Interface: http://localhost:8080
   - H2 Database Console: http://localhost:8080/h2-console
   - REST API Base: http://localhost:8080/api

### Database Configuration

The application uses H2 in-memory database by default with the following settings:
- **URL**: `jdbc:h2:mem:ecommerce_db`
- **Username**: `sa`
- **Password**: (empty)

Sample data is automatically loaded on startup from `data.sql`.

## API Endpoints

### Products
- `GET /api/products` - Get all products
- `GET /api/products/{id}` - Get product by ID
- `POST /api/products` - Create new product
- `PUT /api/products/{id}` - Update product
- `DELETE /api/products/{id}` - Delete product
- `GET /api/products/featured` - Get featured products
- `GET /api/products/search?keyword={keyword}` - Search products

### Categories
- `GET /api/categories` - Get all categories
- `GET /api/categories/{id}` - Get category by ID
- `POST /api/categories` - Create new category
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}` - Delete category

### Users
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users/register` - Register new user
- `POST /api/users/login` - User login
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Orders
- `GET /api/orders` - Get all orders
- `GET /api/orders/{id}` - Get order by ID
- `GET /api/users/{userId}/orders` - Get orders by user
- `POST /api/orders` - Create new order
- `PUT /api/orders/{id}/status` - Update order status
- `DELETE /api/orders/{id}` - Delete order

### Reviews
- `GET /api/reviews` - Get all reviews
- `GET /api/reviews/{id}` - Get review by ID
- `GET /api/reviews/product/{productId}` - Get reviews for product
- `GET /api/reviews/user/{userId}` - Get reviews by user
- `POST /api/reviews` - Create new review
- `PUT /api/reviews/{id}` - Update review
- `DELETE /api/reviews/{id}` - Delete review

## Web Interface

The application provides a complete web interface accessible at http://localhost:8080:

- **Home Page**: Featured products and categories
- **Products Page**: Browse all products
- **Product Details**: View individual product with reviews
- **Categories Page**: Browse product categories
- **About Page**: Information about the store

## Key Improvements from Microservices

1. **Simplified Deployment**: Single JAR file instead of multiple services
2. **Better Performance**: Direct method calls instead of HTTP calls between services
3. **ACID Transactions**: Full transaction support across all entities
4. **Simplified Configuration**: Single configuration file
5. **Easier Development**: No need to manage multiple services during development

## Development Notes

- The application uses proper JPA relationships between entities (instead of storing just IDs)
- Product ratings are automatically updated when reviews are added/modified/deleted
- Stock quantities are automatically updated when orders are placed
- Sample data includes products, categories, users, orders, and reviews for testing

## Future Enhancements

- Add authentication and authorization
- Implement shopping cart functionality
- Add payment processing
- Implement email notifications
- Add product image upload
- Switch to persistent database (MySQL/PostgreSQL)
- Add caching layer
- Implement search functionality
- Add admin dashboard
