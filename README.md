
# Solidarity

## Description

Solidarity is a task scheduling application that allows users to create, join, and manage tasks. It is implemented using Java with Spring Boot for the backend, and utilizes Spring Security for authentication and JWT tokens for secure access.
![relationship](https://user-images.githubusercontent.com/71747119/234400343-ae0bd278-fd59-4ada-9b90-dc4044b586db.png)
backend: https://github.com/the0ss/Solidarity


## Table of Contents

- [Features](#Features)
- [API Documentation](#API-Documentation)
- [Technologies-Used](#Technologies-Used)

## Features
-   Solidarity uses Spring Security to provide secure authentication for users. Users can register, login, and logout from the application.
-   JSON Web Tokens (JWT) used for authentication. JWT tokens are generated upon successful login and are used for subsequent API requests to authenticate and authorize users.The API is secured using JWT tokens for authentication and authorization. Solidarity implements role-based access control with two roles: Admin and User. Admins have additional privileges such as deleteting other users.
-   Users can create, join, and manage tasks. Tasks have a many-to-many relationship with users, allowing multiple users to join a task. Task creators have the ability to delete tasks, mark tasks as completed, and view tasks they have

## API-Documentation

Solidarity provides a RESTful API for managing tasks. Below are the endpoints available in the API:

-   `POST /api/auth/register`: Register a new user.
-   `POST /api/auth/authenticate`: Login a user and obtain a JWT token.
-   `POST /api/admin/delete/{id}`: Delete a user( requesting user must have **ADMIN** role).
-   `GET /api/tasks/{id}/listUsers`: Get all users for a tasks.
-   `GET /api/tasks/alltasks`: Get all tasks.
-   `POST /api/tasks/addtask`: Create a new task.
-   `PUT /api/tasks/markComplete/{id}`: Mark a task as completed( requesting user has to be creator of the task).
-   `DELETE /api/tasks/removeTask/{id}`: Delete a task by ID( user has to be creator of the task).
-   `POST /api/tasks/{id}/join`: Join a task by ID.
-   `POST /api/users/joinTask/{id}`: Join a task.


Note: JWT token obtained during login needs to be included in the Authorization header of subsequent API requests for authentication.



## Technologies-Used

-   Java
-   Spring Boot
-   Spring Security
-   JWT
