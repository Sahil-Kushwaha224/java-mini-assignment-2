# Java Mini Assignment 2

## Overview

This project is a Java Spring Boot application that performs the following tasks:
1. Retrieves random user data from an external API.
2. Validates the user's nationality and gender using additional external APIs.
3. Stores the user data in a database, marking the user as `VERIFIED` or `TO_BE_VERIFIED` based on the validation.
4. Provides RESTful APIs to create and retrieve user data with sorting and pagination features.

## Features

- **Create Users**: Create users by fetching random data from an external API.
- **Validate User Data**: Check if the nationality and gender match using additional APIs.
- **Store User Data**: Save the verified or unverified user data in a database.
- **Retrieve Users**: Retrieve a list of users with support for sorting and pagination.
- **Custom Validators**: Includes custom validators to validate input parameters.
- **Error Handling**: Returns customized error messages.
- **Design Patterns**: Implements SOLID principles, strategy design pattern, and Singleton pattern.

## Technologies Used

- **Java 8+**
- **Spring Boot**
- **Spring Data JPA**
- **WebClient**
- **MySQL**
- **JUnit & Mockito**

## How to Run the Project

1. **Clone the repository**:
    ```bash
    git clone https://github.com/your-username/java-mini-assignment-2.git
    cd java-mini-assignment-2
    ```

2. **Build the project**:
    ```bash
    ./mvnw clean install
    ```

3. **Run the project**:
    ```bash
    ./mvnw spring-boot:run
    ```

4. **APIs**:
    - **Create Users**: 
      - POST: `http://localhost:8080/users`
      - Payload: `{ "size": 1 }`
    - **Get Users**: 
      - GET: `http://localhost:8080/users`
      - Query Params: `?sortType=Name&sortOrder=even&limit=5&offset=0`
