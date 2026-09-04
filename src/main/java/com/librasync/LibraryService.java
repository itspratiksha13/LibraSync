package com.librasync;

import java.sql.SQLException;
import java.util.List;

public class LibraryService {
    private final BookDAO bookDAO = new BookDAO();
    private final ReservationDAO reservationDAO = new ReservationDAO();

    // Critical section: only one reservation request can check/update the state at a time.
    public synchronized String reserveBook(int userId, int bookId) {
        try {
            Book book = bookDAO.findById(bookId);
            if (book == null) return "Book not found.";

            if (book.getAvailableCopies() > 0) {
                bookDAO.borrow(bookId);
                return "Book was available, so User " + userId + " received a copy.";
            }

            if (reservationDAO.hasActiveReservation(userId, bookId)) {
                return "User " + userId + " already has an active reservation.";
            }

            reservationDAO.create(userId, bookId);
            int position = reservationDAO.countActiveReservations(bookId);
            return "Book unavailable. User " + userId + " joined reservation queue at position " + position + ".";
        } catch (SQLException | IllegalStateException e) {
            return "Reservation failed: " + e.getMessage();
        }
    }

    public List<Book> searchBooks(String keyword) throws SQLException {
        return bookDAO.search(keyword);
    }

    public List<Book> allBooks() throws SQLException {
        return bookDAO.findAll();
    }

    public void addBook(String title, String author, String category, int copies) throws SQLException {
        if (title.isBlank() || author.isBlank() || category.isBlank()) {
            throw new IllegalArgumentException("Fields cannot be empty.");
        }
        if (copies < 1) throw new IllegalArgumentException("Copies must be at least 1.");
        bookDAO.addBook(title, author, category, copies);
    }

    public void borrowBook(int bookId) throws SQLException {
        bookDAO.borrow(bookId);
    }

    public void returnBook(int bookId) throws SQLException {
        bookDAO.returnBook(bookId);
    }

    public List<String> reservationQueue(int bookId) throws SQLException {
        return reservationDAO.findWaitingForBook(bookId);
    }
}
