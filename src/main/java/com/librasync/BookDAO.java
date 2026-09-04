package com.librasync;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public void addBook(String title, String author, String category, int copies) throws SQLException {
        String sql = "INSERT INTO books(title, author, category, total_copies, available_copies) VALUES(?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, title); ps.setString(2, author); ps.setString(3, category);
            ps.setInt(4, copies); ps.setInt(5, copies);
            ps.executeUpdate();
        }
    }

    public List<Book> findAll() throws SQLException {
        return search("");
    }

    public List<Book> search(String keyword) throws SQLException {
        List<Book> list = new ArrayList<>();
        String sql = """
            SELECT * FROM books
            WHERE lower(title) LIKE lower(?) OR lower(author) LIKE lower(?) OR lower(category) LIKE lower(?)
            ORDER BY id
            """;
        String k = "%" + keyword + "%";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, k); ps.setString(2, k); ps.setString(3, k);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Book(rs.getInt("id"), rs.getString("title"),
                            rs.getString("author"), rs.getString("category"),
                            rs.getInt("total_copies"), rs.getInt("available_copies")));
                }
            }
        }
        return list;
    }

    public Book findById(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM books WHERE id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Book(rs.getInt("id"), rs.getString("title"),
                            rs.getString("author"), rs.getString("category"),
                            rs.getInt("total_copies"), rs.getInt("available_copies"));
                }
            }
        }
        return null;
    }

    public void borrow(int bookId) throws SQLException {
        String sql = "UPDATE books SET available_copies=available_copies-1 WHERE id=? AND available_copies>0";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            if (ps.executeUpdate() == 0) throw new IllegalStateException("No available copy.");
        }
    }

    public void returnBook(int bookId) throws SQLException {
        String sql = "UPDATE books SET available_copies=MIN(total_copies, available_copies+1) WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            if (ps.executeUpdate() == 0) throw new IllegalArgumentException("Book not found.");
        }
    }
}
