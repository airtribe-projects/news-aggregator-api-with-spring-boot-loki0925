# Personalized News API

A Spring Boot REST API for personalized news based on user preferences. This application allows users to register, log in, set their news preferences, and retrieve news articles tailored to their interests.

## Features

- User authentication using JWT tokens
- User registration and login
- Manage user news preferences by category
- Fetch personalized news based on user preferences
- In-memory H2 database for quick testing and development

## API Endpoints

- `POST /api/register`: Register a new user
- `POST /api/login`: Log in a user and receive JWT token
- `GET /api/preferences`: Retrieve the news preferences for the logged-in user
- `PUT /api/preferences`: Update the news preferences for the logged-in user
- `GET /api/news`: Fetch news articles based on the logged-in user's preferences




## Testing the API

You can test the API using Postman or curl. Here are some examples:

### Register a new user

```bash
curl -X POST http://localhost:8080/api/register \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","password":"password123"}'
```

### Login

```bash
curl -X POST http://localhost:8080/api/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"password123"}'
```

### Set preferences (requires authentication)

```bash
curl -X PUT http://localhost:8080/api/preferences \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{"categories":["TECHNOLOGY","BUSINESS","SPORTS"]}'
```

### Get news (requires authentication)

```bash
curl -X GET http://localhost:8080/api/news \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```


