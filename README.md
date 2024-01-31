# Customer Management Application

This project manages customer information and provides authentication functionality.

# Table of Contents

1. [Customer Management Model](#customer-management-model)
   - [Customer Class](#customer-class)
   - [AuthRequest Class](#authrequest-class)
   - [AuthResponse Class](#authresponse-class)
   - [JwtRequest Class](#jwtrequest-class)
   - [JwtResponse Class](#jwtresponse-class)
   - [CustomerDto Class](#customerdto-class)
   - [CustomerResponse Class](#customerresponse-class)

2. [Customer Management Service](#customer-management-service)
   - [CustomerService Class](#customerservice-class)
   - [SyncService Class](#syncservice-class)

3. [Customer Management Repository](#customer-management-repository)
   - [CustomerRepository Interface](#customerrepository-interface)

4. [Customer Management Controller](#customer-management-controller)
   - [CustomerController Class](#customercontroller-class)
   - [WebAuthController Class](#webauthcontroller-class)
   - [AuthController Class](#authcontroller-class)

5. [Application Configuration](#application-configuration)
   - [AppConfig Class](#appconfig-class)
   - [MyLoginConfig Class](#myloginconfig-class)
   - [RemoteApiConfig Class](#remoteapiconfig-class)
   - [SecurityConfig Class](#securityconfig-class)

6. [Security Classes](#security-classes)
   - [JwtAuthenticationEntryPoint Class](#jwtauthenticationentrypoint-class)
   - [JwtAuthenticationFilter Class](#jwtauthenticationfilter-class)
   - [JwtHelper Class](#jwthelper-class)

## Customer Management Model Classes

### Customer

The `Customer` class represents a customer entity in the application. It is an entity class annotated with JPA annotations for persistence. This class includes attributes such as first name, last name, street, address, city, state, email, and phone. Additionally, various validation annotations from Jakarta Bean Validation and Hibernate Validator are used to ensure data integrity. The class is also equipped with Lombok annotations for boilerplate code reduction.

### AuthRequest

The `AuthRequest` class is a simple model representing an authentication request. It includes attributes for login ID and password. This class is used to encapsulate user credentials during the authentication process.

### AuthResponse

The `AuthResponse` class represents the response generated during the authentication process. It includes an access token, and the class is annotated with Jackson's `@JsonProperty` to customize the JSON property name.

### JwtRequest

The `JwtRequest` class is a model used for encapsulating JWT (JSON Web Token) authentication requests. It includes attributes for email and password, representing the user's credentials during the JWT authentication process.

### JwtResponse

The `JwtResponse` class represents the response generated after a successful JWT authentication. It includes attributes for the JWT token and the username associated with the token.

### CustomerDto

The `CustomerDto` class is a Data Transfer Object (DTO) representing customer data. It includes attributes such as first name, last name, street, address, city, state, email, and phone. This class is commonly used to transfer data between different layers of the application, such as between the frontend and backend.

### CustomerResponse

The `CustomerResponse` class is another DTO representing customer data, but with attribute names formatted differently. It includes attributes like uuid, first_name, last_name, street, address, city, state, email, and phone. This class might be used for shaping the response data in a specific way for API responses.



## Customer Management Service Classes

### CustomerService

The `CustomerService` class is responsible for managing customer-related operations. It interacts with the `CustomerRepository` to perform CRUD (Create, Read, Update, Delete) operations on customer entities. Additionally, it provides methods for retrieving customer lists based on various criteria and synchronizing customer data.

#### Methods

- `createCustomer(CustomerDto customerDto)`: Creates a new customer based on the provided `CustomerDto` and saves it to the repository.
- `getCustomer(String customerId)`: Retrieves a customer by ID from the repository.
- `updateCustomer(String customerId, CustomerDto customerDto)`: Updates an existing customer based on the provided ID and `CustomerDto`.
- `deleteCustomer(String customerId)`: Deletes a customer by ID from the repository.
- `getAllCustomerList(int page, int size)`: Retrieves a paginated list of all customers from the repository.
- `getAllCustomer(int page, int size, String searchCriteria, String searchValue)`: Retrieves a paginated list of customers based on the specified search criteria and value.
- `syncCustomerInData(List<CustomerResponse> list)`: Synchronizes customer data by updating existing customers or adding new ones based on the provided `CustomerResponse` list.

### SyncService

The `SyncService` class is responsible for handling synchronization with a remote API. It includes methods to authenticate and obtain an access token and to sync customer data from the remote API.

#### Methods

- `initialize()`: Initializes the service, such as adding a message converter with UTF-8 encoding.
- `authenticateAndGetToken()`: Authenticates with the remote API and retrieves an access token.
- `parseJsonResponse(String responseBody, Class<T> responseType)`: Parses JSON response and converts it to the specified Java class.
- `syncCustomers(String token)`: Fetches customer data from the remote API using the provided access token.

Both services use Lombok annotations (`@RequiredArgsConstructor`, `@Slf4j`) for simplified constructor creation and logging. The `SyncService` also utilizes a `RestTemplate` for making HTTP requests and a configuration class (`RemoteApiConfig`) for storing remote API details. Ensure that the necessary configurations, such as API URLs and credentials, are correctly set in the `RemoteApiConfig` class.

## Customer Management Controller Classes

### CustomerController

The `CustomerController` class handles HTTP requests related to customer management. It interacts with the `CustomerService` to perform operations such as creating, updating, retrieving, and deleting customers. Additionally, it provides endpoints for retrieving lists of customers with pagination, sorting, and searching.

#### Endpoints
- `POST /api/customers`: Creates a new customer based on the provided `CustomerDto`.
- `PUT /api/customers/{customerId}`: Updates an existing customer based on the provided ID and `CustomerDto`.
- `GET /api/customers`: Retrieves a paginated list of customers with optional sorting and searching parameters.
- `GET /api/customers/{customerId}`: Retrieves a single customer based on ID.
- `DELETE /api/customers/{customerId}`: Deletes a customer based on ID.

### WebAuthController

The `WebAuthController` class handles web-based authentication and customer-related views. It provides endpoints for rendering login, home, add, update, and synchronization forms/views.

#### Endpoints
- `GET /web/login`: Renders the login form.
- `GET /web/home`: Renders the home view.
- `GET /web/add`: Renders the customer addition form.
- `GET /web/update/{customerId}`: Renders the customer update form based on the provided customer ID.
- `GET /web/sync`: Initiates synchronization with a remote API, updating customer data.

### AuthController

The `AuthController` class handles user authentication through HTTP requests. It utilizes Spring Security and JWT for user authentication and token generation.

#### Endpoints
- `POST /login/auth`: Authenticates a user based on the provided credentials and generates a JWT token.

Both the `CustomerController` and `WebAuthController` classes use Lombok annotations (`@RequiredArgsConstructor`) for simplified constructor creation. Additionally, the `AuthController` class utilizes Spring Security's `AuthenticationManager` for user authentication and a `JwtHelper` for JWT token generation.

Ensure that appropriate security configurations are in place, and the necessary dependencies are available for Spring Security and JWT.

# Configuration Classes

## AppConfig

The `AppConfig` class centralizes configuration for the Customer Management application. It defines essential beans used in the application, such as `RestTemplate`, `UserDetailsService`, `PasswordEncoder`, and `AuthenticationManager`.

### Beans
1. **restTemplate():** Creates a `RestTemplate` bean for making RESTful API calls.
2. **userDetailsService():** Creates a `UserDetailsService` bean using in-memory user details with a single admin user.
3. **passwordEncoder():** Creates a `PasswordEncoder` bean, specifically using `BCryptPasswordEncoder`.
4. **authenticationManager(AuthenticationConfiguration builder):** Creates an `AuthenticationManager` bean using the provided `AuthenticationConfiguration`.

## MyLoginConfig

The `MyLoginConfig` class is a component responsible for reading properties from `application.properties` related to the login API, such as the username and password.

### Properties
- **Username:** The username for the login API.
- **Password:** The password for the login API.

## RemoteApiConfig

The `RemoteApiConfig` class is a component responsible for reading properties from `application.properties` related to a remote API, including credentials and URLs.

### Properties
- **Username:** The username for accessing the remote API.
- **Password:** The password for accessing the remote API.
- **authApiUrl:** The URL for the authentication endpoint of the remote API.
- **GetApiUrl:** The URL for the GET endpoint of the remote API.

## SecurityConfig

The `SecurityConfig` class configures security settings for the Customer Management application. It defines security rules, exception handling, and filters, including JWT authentication.

### Configuration
- **securityFilterChain(HttpSecurity http):** Configures security settings, allowing specific endpoints to be accessed without authentication. It defines an exception handling mechanism and includes a JWT authentication filter.

Ensure that the properties in `MyLoginConfig` and `RemoteApiConfig` are correctly set in the `application.properties` file for the application to work seamlessly. Customize security rules in `SecurityConfig` as needed for your specific use case.
# Security Classes

## JwtAuthenticationEntryPoint

The `JwtAuthenticationEntryPoint` class handles unauthorized access by implementing the `AuthenticationEntryPoint` interface. It sends an unauthorized response if a user tries to access a secured resource without proper authentication.

### Methods
- **commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException):** Sends an unauthorized response with a custom message.

## JwtAuthenticationFilter

The `JwtAuthenticationFilter` class is an implementation of the `OncePerRequestFilter` that intercepts and validates JWT tokens in the HTTP request header. It performs JWT authentication and sets the authenticated user in the `SecurityContextHolder`.

### Methods
- **doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain):** Performs JWT authentication and sets the authenticated user in the `SecurityContextHolder`.

## JwtHelper

The `JwtHelper` class provides utility methods for working with JWT tokens. It includes methods for generating, validating, and extracting information from JWT tokens.

### Methods
- **getUsernameFromToken(String token):** Retrieves the username from a JWT token.
- **getExpirationDateFromToken(String token):** Retrieves the expiration date from a JWT token.
- **getClaimFromToken(String token, Function<Claims, T> claimsResolver):** Retrieves a specific claim from a JWT token.
- **generateToken(UserDetails userDetails):** Generates a JWT token for a user.
- **validateToken(String token, UserDetails userDetails):** Validates a JWT token against a user's details.

Ensure that the `secret` used for JWT token generation is kept secure, and consider using environment variables for sensitive information.

Make sure to properly configure these security classes in your Spring Security configuration (`SecurityConfig`) for effective integration.
