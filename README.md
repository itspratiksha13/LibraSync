#  LibraSync

### Smart Library Management System with Concurrent Book Reservation

LibraSync is a command-line based Library Management System developed in Java. It manages users, books, borrowing and returning, book reservations, and reservation queues.

The project also demonstrates concurrent book reservation using Java multithreading and synchronization, along with database operations using JDBC and SQLite.

---

## Features

- User registration and login
- Role-based user access
- Add, search, and view books
- Borrow and return books
- Reserve unavailable books
- Manage reservation queues
- Concurrent reservation using multiple threads
- Synchronization to handle concurrent access
- Library reports
- Persistent data storage using SQLite

---

## Technologies Used

- *Language:* Java
- *JDK:* 17+
- *Build Tool:* Maven
- *Database:* SQLite
- *Database Connectivity:* JDBC
- *Interface:* Command Line Interface (CLI)

---

## Java Concepts Demonstrated

The project applies the following Java concepts:

- Object-Oriented Programming
- Classes and Objects
- Constructors and Methods
- Encapsulation
- Exception Handling
- Collections Framework
- Strings and Arrays
- Multithreading
- Synchronization
- Packages
- JDBC
- SQLite Database Operations


---

## Main Modules

### 1. User Management
Handles user registration, login, authentication, and user roles.

### 2. Book Management
Allows books to be added, searched, and viewed along with their availability.

### 3. Borrow & Return
Manages borrowing and returning of available books.

### 4. Book Reservation
Allows users to reserve books that are currently unavailable and maintains a reservation queue.

### 5. Concurrent Reservation
Demonstrates multiple users attempting to reserve the same book concurrently using Java threads and synchronization.

### 6. Reports
Provides library-related information and summary reports through the command-line interface.

---

## Project Structure

```text
LibraSync/
│
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── librasync/
│                   ├── Main.java
│                   ├── ConsoleUI.java
│                   ├── LibraryService.java
│                   ├── ConcurrentReservationDemo.java
│                   ├── DBConnection.java
│                   ├── DatabaseInitializer.java
│                   ├── User.java
│                   ├── Book.java
│                   ├── Reservation.java
│                   ├── UserDAO.java
│                   ├── BookDAO.java
│                   └── ReservationDAO.java
│
├── pom.xml
├── README.md
├── statement.md
└── .gitignore
```
## Requirements

- JDK 17+
- Maven 3.8+
- Internet connection for the first Maven build to download the required dependencies

No separate database installation is required. LibraSync uses SQLite, and the database is created automatically when the application runs.

---

## Run

### 1. Clone the repository

```bash
git clone https://github.com/itspratiksha13/LibraSync.git
cd LibraSync
```

### 2. Build the project and install dependencies

Maven automatically downloads the required project dependencies during the build.

```bash
mvn clean package
```

### 3. Run the application

```bash
java -jar target/librasync-1.0.jar
```

### 4. Database configuration

No manual database configuration is required. The SQLite database is automatically created at:

```text
data/librasync.db
```

when the application is run.

---

## Default Demo Users

*Admin*
- Username: admin
- Password: admin123

*Student*
- Username: student1
- Password: student123

---

## Author

*Pratiksha*
