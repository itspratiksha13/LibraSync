# LibraSync

**Smart Library Management System with Concurrent Book Reservation**

A command-line Java application demonstrating OOP, Collections, Exception Handling,
Multithreading/Synchronization, JDBC, arrays/strings, and modular package design.

## Requirements
- JDK 17+
- Maven 3.8+

## Run
```bash
mvn clean package
java -jar target/librasync-1.0.jar
```

The SQLite database is created automatically as `data/librasync.db`.

## Main modules
1. User Management
2. Book Management
3. Borrow/Return
4. Book Reservation
5. Concurrent Reservation Test
6. Reports

## Default demo users
- Admin: `admin` / `admin123`
- Student: `student1` / `student123`
