Registration Application
The Registration Application is a Spring Boot application that provides user registration functionality.

Features
User registration: Allows users to register with their information.
User authentication: Provides secure authentication using JSON Web Tokens (JWT).
Role-based access control: Supports different user roles (user and admin) with corresponding access permissions.
Error handling: Includes proper error handling and response messages for various scenarios.

Technologies Used
Java 11
Spring Boot 2.7.12
Spring Security
JSON Web Tokens (JWT)
MySQL

Prerequisites
Java Development Kit (JDK) 8 or higher
MySQL
Maven build tool

Set up the database:
Create a new database in your MySQL server.
Update the database configuration in the application.properties file with your database credentials and connection details.

Build the application:
mvn clean install

Usage
Run the application:
mvn spring-boot:run

Access the application in your web browser:
http://localhost:8080
Register a new user by providing the required information.
Log in with your registered credentials.
by default the registered user will take the role of "user"
you can update it from your Mysql server to "admin"

API Endpoints
The Registration Application exposes the following API endpoints:

POST /user/register: Register a new user.
POST /user/login: Authenticate and obtain an access token.
GET /user/users/all: Retrieve all users' information (access only for admin).
GET /user/{userid}: Retrieve user information by user (ID access only for admin).
POST /logout : user logout

Security Considerations
User passwords are securely hashed using BCryptPasswordEncoder.
User authentication is performed using JWT.
Access to certain endpoints may require specific user roles.
Proper exception handling and error responses are implemented.

Acknowledgments
Spring Boot - Framework for building Java applications.
Spring Security - Framework for securing Spring applications.
JSON Web Tokens - Industry standard for secure authentication.
MySQL - Open-source relational database management system.