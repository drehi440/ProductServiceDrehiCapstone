## User Management API Testing

The application exposes a JWT-based user management module under the `com.rehi.productservicedrehicapstone` package.
This section provides ready-to-use examples for registering a user, logging in, and accessing the authenticated profile.

> Replace `localhost:8080` if your server is running on a different host or port.

### 1. Register a New User

**Endpoint:** `POST /api/auth/register`  
**Description:** Creates a new user account and returns a JWT token with basic user info.  
**Default Role:** `CUSTOMER`

```bash
curl -X POST "http://localhost:8080/api/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "d,r@example.com",
    "password": "StrongPassword123",
    "firstName": "D",
    "lastName": "R",
    "mobileNumber": "1234567890"
  }'
```

**Sample JSON request body:**

```json
{
  "email": "d.r@example.com",
  "password": "StrongPassword123",
  "firstName": "D",
  "lastName": "R",
  "mobileNumber": "1234567890"
}
```

The response will include a `jwtToken`, `userId`, and `username` (email).

### 2. Login and Copy the Token

**Endpoint:** `POST /api/auth/login`  
**Description:** Authenticates an existing user and returns a new JWT token.

```bash
curl -X POST "http://localhost:8080/api/auth/login" \
  -H "Content-Type: application/json" \
  -d '{
    "email": "d.r@example.com",
    "password": "StrongPassword123"
  }'
```

**Sample JSON request body:**

```json
{
  "email": "d.r@example.com",
  "password": "StrongPassword123"
}
```

From the JSON response, copy the value of the `jwtToken` field.  
This token will be used as a **Bearer token** in the `Authorization` header for secured endpoints.

### 3. Access the Profile Using the Bearer Token

**Endpoint:** `GET /api/users/profile`  
**Description:** Retrieves the profile of the currently authenticated user.  
**Authorization:** Requires a valid JWT token in the `Authorization` header.

```bash
TOKEN="PASTE_JWT_TOKEN_HERE"

curl -X GET "http://localhost:8080/api/users/profile" \
  -H "Authorization: Bearer $TOKEN"
```

### 4. Update the Profile Using the Bearer Token

**Endpoint:** `PUT /api/users/profile`  
**Description:** Updates non-sensitive profile details (first name, last name, mobile number).  
**Authorization:** Requires a valid JWT token.

```bash
TOKEN="PASTE_JWT_TOKEN_HERE"

curl -X PUT "http://localhost:8080/api/users/profile" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "firstName": "Deep",
    "lastName": "Rehi",
    "mobileNumber": "9876543210",
    "email": "deep.rehi@example.com"
  }'
```

### 5. Reset Password Using the Bearer Token

**Endpoint:** `POST /api/auth/reset-password`  
**Description:** Resets the password for the currently authenticated user.  
**Authorization:** Requires a valid JWT token.

```bash
TOKEN="PASTE_JWT_TOKEN_HERE"

curl -X POST "http://localhost:8080/api/auth/reset-password" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "oldPassword": "StrongPassword123",
    "newPassword": "NewStrongPassword456"
  }'
```

If the old password is incorrect, the API will return a 401 Unauthorized error with a helpful error message handled by the global exception handler.


