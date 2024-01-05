# Notes-Application

RESTful APIs Implementation using Java Spring Boot

This repository contains a RESTful API implementation using Java Spring Boot for managing notes. Below, you will find information about the technology choices, authentication process, and the available API endpoints.

Technology Stack

Java Spring Boot:

The application is built using Java Spring Boot due to its simplicity, ease of development, and robustness. Spring Boot provides a convenient way to build RESTful services with minimal configuration, allowing for rapid development and easy integration with various components.

PostgreSQL Database:

The application uses a PostgreSQL database to store user information and notes. PostgreSQL is a powerful, open-source relational database management system known for its reliability and performance. It provides features such as ACID compliance and support for complex queries, making it suitable for this application.

JWT Token Authentication:

JSON Web Token (JWT) is employed for user authentication. JWT is a compact, URL-safe means of representing claims between two parties. In this application, JWT tokens are generated upon successful user registration or login and are used to authenticate subsequent requests.

# API Endpoints

1. User Registration

Endpoint: POST /api/auth/signup

Input

{

  "firstName": "John",
  
  "lastName": "Doe",
  
  "userName": "john_doe",
  
  "password": "securePassword",
  
  "confirmPassword": "securePassword"
  
}


Functionality:

Validates input parameters.
Checks if the username is unique.
Verifies password and confirm password match.
Creates a new user, generates a JWT token with a 1-day expiration, and returns it in the response.

2. User Login

Endpoint: POST /api/auth/login

Input

{

  "userName": "john_doe",
  
  "password": "securePassword"

}


Functionality:

Validates input parameters.
Authenticates the user based on the provided credentials.
Returns a JWT token in the response upon successful authentication.


3. Get All Notes

Endpoint: GET /api/notes

Header: username, verify_token

Functionality:

Retrieves all notes associated with the authenticated user.
Returns the notes in the response object.


4. Get Note by ID

Endpoint: GET /api/notes/{id}

Header: username, verify_token


Functionality:

Retrieves a specific note by its ID if it belongs to the authenticated user.
Returns the note in the response object.


5. Create a Note

Endpoint: POST /api/notes

Header: username, verify_token

Input:

{

  "description": "This is a sample Note"

}


Functionality:

Creates a note and maps it to the user in the db



6. Update Note by ID

Endpoint: PUT /api/notes/{id}

Header: username, verify_token

Input:

{

  "description": "This is a sample Note"

}


Functionality:

Updates the description of a specific note by its ID if it belongs to the authenticated user.
Returns the updated note in the response object.


7. Delete Note by ID

Endpoint: DELETE /api/notes/{id}

Header: username, verify_token


Functionality:

Deletes a specific note by its ID if it belongs to the authenticated user.
Returns a success message in the response object.


8. Share Note by ID

Endpoint: POST /api/notes/{id}/share

Header: username, verify_token

Input:

{

  "sharedUserName": "john_doe"

}


Functionality:

Shares a specific note with another user by adding an entry to the shared_notes table.
Returns a success message in the response object.


9. Search Notes

Endpoint: GET /api/search?q=query

Header: username, verify_token


Functionality:

Searches for notes (case-insensitive) based on the provided query string in the description.
Returns matched notes in the response object.


# Response Object
{

  "successful": true/false,

  "status": <HTTPStatus>,

  "responseObject": <>,

  "error": <>,

}


ERROR STRUCTURE: 

{

  "code": "InvalidRequest",
 
  "message": "User doesn't exist"

}


# Getting Started


-> Clone the repository - git clone https://github.com/Mitul0911/Notes-Application.git

-> Configure the PostgreSQL database settings in application.properties

-> Run the application using your preferred method (mvn spring-boot:run or try any IDE)


# Use the below postman collection for testing:

postman collection link: https://martian-resonance-10210.postman.co/workspace/Team-Workspace~578ed934-02a6-469a-8fd0-df6388392e66/collection/25041051-811ad346-4ae2-40ee-9e8c-1426d2ce6d4d?action=share&creator=25041051
