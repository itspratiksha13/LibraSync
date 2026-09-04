package com.librasync;

import java.sql.*;

public class DatabaseInitializer {

    public void initialize() {
        String users = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                username TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL,
                role TEXT NOT NULL
            )
            """;

        String books = """
            CREATE TABLE IF NOT EXISTS books (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                author TEXT NOT NULL,
                category TEXT NOT NULL,
                total_copies INTEGER NOT NULL CHECK(total_copies >= 1),
                available_copies INTEGER NOT NULL CHECK(available_copies >= 0)
            )
            """;

        String reservations = """
            CREATE TABLE IF NOT EXISTS reservations (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                book_id INTEGER NOT NULL,
                status TEXT NOT NULL,
                reserved_at TEXT NOT NULL,
                FOREIGN KEY(user_id) REFERENCES users(id),
                FOREIGN KEY(book_id) REFERENCES books(id)
            )
            """;

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement()) {
            st.execute(users);
            st.execute(books);
            st.execute(reservations);
            seedData(con);
        } catch (SQLException e) {
            throw new RuntimeException("Database initialization failed: " + e.getMessage(), e);
        }
    }

    private void seedData(Connection con) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM users");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next() && rs.getInt(1) == 0) {
                String sql = "INSERT INTO users(name, username, password, role) VALUES(?,?,?,?)";
                try (PreparedStatement ins = con.prepareStatement(sql)) {
                    addUser(ins, "System Admin", "admin", "admin123", "ADMIN");
                    addUser(ins, "Demo Student 1", "student1", "student123", "STUDENT");
                    addUser(ins, "Demo Student 2", "student2", "student123", "STUDENT");
                    addUser(ins, "Demo Student 3", "student3", "student123", "STUDENT");
                    addUser(ins, "Demo Student 4", "student4", "student123", "STUDENT");
                    addUser(ins, "Demo Student 5", "student5", "student123", "STUDENT");
                }
            }
        }

        try (PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM books");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next() && rs.getInt(1) == 0) {
                String sql = "INSERT INTO books(title, author, category, total_copies, available_copies) VALUES(?,?,?,?,?)";
                try (PreparedStatement ins = con.prepareStatement(sql)) {
                    addBook(ins, "Clean Code", "Robert C. Martin", "Programming", 3);
                    addBook(ins, "Java: The Complete Reference", "Herbert Schildt", "Programming", 2);
                    addBook(ins, "The Alchemist", "Paulo Coelho", "Fiction", 2);
                    addBook(ins, "Atomic Habits", "James Clear", "Self Help", 4);
                }
            }
        }
    }

    private void addUser(PreparedStatement ps, String n, String u, String p, String r) throws SQLException {
        ps.setString(1, n); ps.setString(2, u); ps.setString(3, p); ps.setString(4, r);
        ps.executeUpdate();
    }

    private void addBook(PreparedStatement ps, String t, String a, String c, int copies) throws SQLException {
        ps.setString(1, t); ps.setString(2, a); ps.setString(3, c);
        ps.setInt(4, copies); ps.setInt(5, copies);
        ps.executeUpdate();
    }
}
