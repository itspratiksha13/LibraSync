package com.librasync;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner sc = new Scanner(System.in);
    private final UserDAO userDAO = new UserDAO();
    private final LibraryService service = new LibraryService();

    public void start() {
        System.out.println("""

            ==========================================
                     LIBRASYNC
              Smart Library Management System
            ==========================================
            """);

        while (true) {
            System.out.println("""
                1. Login
                2. Register Student
                3. Exit
                """);
            int choice = readInt("Choose: ");

            try {
                switch (choice) {
                    case 1 -> login();
                    case 2 -> register();
                    case 3 -> { System.out.println("Goodbye!"); return; }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }
        }
    }

    private void login() throws SQLException {
        String username = read("Username: ");
        String password = read("Password: ");
        User user = userDAO.authenticate(username, password);

        if (user == null) {
            System.out.println("Invalid username or password.");
            return;
        }

        System.out.println("Welcome, " + user.getName() + "!");
        dashboard(user);
    }

    private void register() throws SQLException {
        String name = read("Name: ");
        String username = read("Username: ");
        String password = read("Password: ");
        userDAO.addUser(name, username, password, "STUDENT");
        System.out.println("Registration successful.");
    }

    private void dashboard(User user) {
        while (true) {
            System.out.println("""

                -------- Dashboard --------
                1. View all books
                2. Search books
                3. Borrow a book
                4. Return a book
                5. Reserve an unavailable book
                6. View reservation queue
                7. Reports
                8. Concurrent reservation demo
                9. Logout
                """);

            int choice = readInt("Choose: ");
            try {
                switch (choice) {
                    case 1 -> printBooks(service.allBooks());
                    case 2 -> printBooks(service.searchBooks(read("Search keyword: ")));
                    case 3 -> borrow();
                    case 4 -> {service.returnBook(readInt("Book ID: "));
                              System.out.println("Book returned successfully.");}
                    case 5 -> System.out.println(service.reserveBook(user.getId(), readInt("Book ID: ")));
                    case 6 -> printQueue();
                    case 7 -> reports();
                    case 8 -> new ConcurrentReservationDemo().run(service, readInt("Book ID: "));
                    case 9 -> { return; }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (SQLException | InterruptedException | IllegalArgumentException e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }
    }

    private void borrow() throws SQLException {
        int id = readInt("Book ID: ");
        service.borrowBook(id);
        System.out.println("Book borrowed successfully.");
    }

    private void printBooks(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No books found.");
            return;
        }
        System.out.println("\nID | Title | Author | Category | Availability");
        books.forEach(System.out::println);
    }

    private void printQueue() throws SQLException {
        int id = readInt("Book ID: ");
        List<String> queue = service.reservationQueue(id);
        if (queue.isEmpty()) System.out.println("No waiting reservations.");
        else {
            System.out.println("Reservation Queue:");
            for (int i = 0; i < queue.size(); i++) {
                System.out.println((i + 1) + ". " + queue.get(i));
            }
        }
    }

    private void reports() throws SQLException {
        List<Book> books = service.allBooks();
        int total = books.stream().mapToInt(Book::getTotalCopies).sum();
        int available = books.stream().mapToInt(Book::getAvailableCopies).sum();
        System.out.println("\n----- Library Report -----");
        System.out.println("Different titles : " + books.size());
        System.out.println("Total copies     : " + total);
        System.out.println("Available copies : " + available);
        System.out.println("Borrowed copies  : " + (total - available));
    }

    private String read(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(read(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
