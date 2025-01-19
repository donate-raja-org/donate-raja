# Donate Raja API

The **Donate Raja API** provides users with the ability to donate, receive, rent, and take rent for items through the "Donate Raja" platform. It is built using **Spring Boot**, **Kotlin**, and **PostgreSQL**.

## Base URL

- **Base URL**: http://localhost:8080/swagger-ui.html
- http://localhost:8080/actuator/health

## Authentication

- OTP-based phone number authentication is used to verify users. JWT authentication will be implemented in future versions for enhanced security.

## Technologies Used

- **Spring Boot**: Backend framework
- **Kotlin**: Programming language
- **PostgreSQL**: Database
- **Docker**: Containerization
- **JWT**: Optional for future version

### 1. **Database Configuration**

Set the following variables for connecting to your PostgreSQL database:

- `DB_URL`: The URL to your PostgreSQL database (e.g., `jdbc:postgresql://localhost:5432/donate_raja`)
- `DB_USERNAME`: Your database username (e.g., `postgres`)
- `DB_PASSWORD`: Your database password (e.g., `password`)

Example:
```bash
export DB_URL=jdbc:postgresql://localhost:5432/donate_raja
export DB_USERNAME=your_db_username
export DB_PASSWORD=your_db_password
```

### 2. JWT Configuration (Future)
If you're planning to use JWT for authentication, you can set the following environment variables:

JWT_SECRET_KEY: Secret key used for signing JWT tokens.
JWT_EXPIRATION_TIME: Expiration time for the JWT token (in milliseconds).
Example:
```bash
export JWT_SECRET_KEY=your_jwt_secret_key
export JWT_EXPIRATION_TIME=3600000  # 1 hour
```

### 3. Server Configuration
Set the following environment variable for your application’s server port:

SERVER_PORT: The port where the API will run (default is 8080).
Example:

```bash
export SERVER_PORT=8080
```
### 4. Application Properties (Optional)
If you're using a .env file or want to load configurations, you can set additional environment variables like:
```bash
export SPRING_PROFILES_ACTIVE=dev  # Set active profile (e.g., dev, prod)
```
### 5. Running the Application
After setting the environment variables, you can run the application using the following command:

```bash
./mvnw spring-boot:run
```

### BD viewer setup
install [postgres_ide](https://dbeaver.io/download/)
* Host: ep-tiny-forest-a17k2xt8.ap-southeast-1.aws.neon.tech
* Database: donateraja
* username: donateraja_owner
* password: #######

### Use plugin alternative to githubcopilot as free plugin
search ub plugins for tabnine 
[tabnine-ai-chat](https://plugins.jetbrains.com/plugin/12798-tabnine-ai-chat--autocomplete-for-javascript-python--more)


