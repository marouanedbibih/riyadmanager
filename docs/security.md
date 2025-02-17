# Security Overview

## Introduction

In today's digital environment, ensuring robust security for your web applications is crucial to protect sensitive data and resources. The backend of this application leverages **Spring Security**, a powerful and customizable framework, to enforce authentication, authorization, and secure communication.

This security setup focuses on using **JWT (JSON Web Tokens)** for stateless authentication, role-based access control (RBAC), and integrates multiple custom components to handle user authentication and error management. This document outlines the key components and strategies implemented in the backend security architecture.

## Key Security Features

### 1. **Stateless Authentication with JWT**

We implement **JWT** for stateless authentication. This means there is no session state on the server, and the user's identity is verified with every request using a token. The key features are:

- **JWT Creation**: After a successful login, the backend generates a JWT token containing user details and roles. This token is returned to the client and is sent with each subsequent request.
- **JWT Validation**: On each request, the server validates the JWT token to ensure the authenticity of the user's identity. If the token is valid, the user can access protected endpoints.

### 2. **Role-Based Access Control (RBAC)**

Access to different API endpoints is restricted based on the roles assigned to users:

- **ADMIN** and **MANAGER** roles have elevated permissions to manage sensitive resources, like guests' information.
- Public endpoints, such as login and registration, are accessible without authentication, but all other resources are protected and require authentication and proper authorization.

### 3. **Custom Authentication Components**

- **Custom Authentication Provider**: We have a custom authentication provider (`CustomAuthProvider`) that handles authenticating users using their username and password. This provider is configured to throw custom exceptions if credentials are incorrect or the user is not found.
- **Custom User Details Service**: The `CustomUserDetailsService` loads user information from the database. If the username does not exist, a `UsernameNotFoundException` is thrown.
- **JWT Filter**: The `JwtFilter` class intercepts all incoming requests, extracts the JWT from the `Authorization` header, and validates it before allowing access to protected resources.

### 4. **Error Handling and Unauthorized Access**

The application ensures that users are given clear feedback when they attempt to access protected resources without proper authentication:

- **Authentication Entry Point**: If a user tries to access a protected resource without a valid token, the `AuthEntryPointJwt` class returns a custom error response, indicating the authentication failure and sending a `401 Unauthorized` status.
- **Custom Error Responses**: When authentication fails, specific error messages are sent back in the response, providing clear information about the nature of the failure (e.g., invalid credentials or expired token).

### 5. **Session Management**

As the application uses JWT for stateless authentication, session management is configured to be **stateless**. This means the server does not maintain session information between requests, improving scalability and security.

### 6. **Endpoint Protection**

- **Public Endpoints**: These include registration (`/api/register`) and login (`/api/login`) routes, which do not require authentication.
- **Protected Endpoints**: Endpoints for managing guests (`/api/guests`, `/api/guests/{id}`, `/api/guests/list`) require either an `ADMIN` or `MANAGER` role for access.
  
### 7. **Password Security**

- **BCrypt Password Encoder**: User passwords are securely stored and compared using the `BCryptPasswordEncoder`. This ensures passwords are hashed before storage, preventing plaintext passwords from being exposed.

## Security Flow

1. **User Login**:
   - The user sends a POST request to the `/api/login` endpoint with their credentials.
   - If the credentials are correct, the server responds with a JWT token.
   
2. **Accessing Protected Resources**:
   - The user sends requests to protected resources (e.g., `/api/guests`) with the JWT token in the `Authorization` header.
   - The server verifies the JWT token using the `JwtFilter`. If valid, the user’s identity is authenticated, and access is granted.
   - Unauthorized or invalid tokens result in a `401 Unauthorized` error with a descriptive message.

3. **Role-Based Authorization**:
   - Role-based access is enforced, where only users with appropriate roles (like `ADMIN` or `MANAGER`) can access specific endpoints for managing guests.

4. **Logout**:
   - As JWT is stateless, there is no session to invalidate. A user simply stops using their token, and it will expire automatically after a set time.

## Conclusion

The security implementation ensures that:

- Only authenticated and authorized users can access sensitive data and resources.
- Role-based access control enforces the principle of least privilege.
- JWT tokens provide a scalable and stateless authentication mechanism.
- Clear error responses and robust security mechanisms ensure a secure and user-friendly experience.

This security architecture provides a strong foundation for building secure backend systems, protecting both user data and system resources.
