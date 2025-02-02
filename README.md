# To-Do List Application

## Overview

The To-Do List Application is a Java EE-based web application that allows users to manage their tasks efficiently. The application includes functionalities for user authentication, task management, and task history tracking.

## Features

- **User Authentication**: Users can log in and create accounts.
- **Task Management**: Users can add, edit, delete, and filter tasks.
- **Task History**: Completed tasks are stored in history and can be viewed or deleted.
- **File Attachments**: Users can upload and manage task-related files.
- **Sorting and Filtering**: Tasks can be sorted by due date, priority, and status.
- **Email notifications**: Email notifications are sent 24 hours before the due date of the task.

## Technologies Used

- **Java EE** (Servlets & JSP)
- **Jakarta Servlet API**
- **Maven**
- **JDBC & PostgreSQL**
- **BCrypt for password hashing**
- **Tomcat Server**
- **Gmail API**

## Servlets

### 1. `LoginServlet.java`

Handles user authentication, including login and account creation.

- **GET**: Displays login and account creation pages.
- **POST**: Processes login credentials and user registration.

### 2. `TasksServlet.java`

Manages user tasks.

- **GET**: Displays the list of tasks, supports filtering and sorting.
- **POST**: Handles adding, editing, and deleting tasks.
- **File Management**: Users can upload and delete task-related files.
- **Contacts Management**: Users can add and remove contacts for shared tasks.

### 3. `HistoryServlet.java`

Manages completed tasks history.

- **GET**: Displays completed tasks.
- **POST**: Allows deletion of tasks from history.

## Setup Instructions

### 1. Prerequisites

Ensure you have the following installed:

- JDK 14 or later
- Apache Tomcat
- PostgreSQL Database
- Maven

### 2. Database Configuration

Create a PostgreSQL database and update the `context.xml` with the appropriate database connection settings.

```xml
<Resource name="jdbc/MyDB" auth="Container" type="javax.sql.DataSource"
          maxTotal="100" maxIdle="30" maxWaitMillis="15000"
          username="your_username" password="your_password"
          driverClassName="org.postgresql.Driver"
          url="jdbc:postgresql://localhost:5432/MyDatabase"/>
```

### 3. Build and Run

1. Clone the repository
2. Build the project using Maven:
   ```sh
   mvn clean install
   ```
3. Deploy the WAR file to Tomcat.
4. Start Tomcat and access the application at `http://localhost:8080/to-do-list/login`

## Usage

- Register a new account or log in.
- Add tasks with descriptions, due dates, and priorities.
- Upload related files and manage task-related contacts.
- View and manage completed tasks in history.

## Future Enhancements

- Implement REST APIs for better scalability.
- Add OAuth-based authentication.
- Implement notifications and reminders.

## License

This project is licensed under the MIT License.
