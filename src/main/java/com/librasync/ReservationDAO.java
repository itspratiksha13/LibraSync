package com.librasync;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    public boolean hasActiveReservation(int userId, int bookId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM reservations WHERE user_id=? AND book_id=? AND status='WAITING'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId); ps.setInt(2, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    public int countActiveReservations(int bookId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM reservations WHERE book_id=? AND status='WAITING'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public void create(int userId, int bookId) throws SQLException {
        String sql = "INSERT INTO reservations(user_id, book_id, status, reserved_at) VALUES(?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId); ps.setInt(2, bookId);
            ps.setString(3, "WAITING"); ps.setString(4, LocalDateTime.now().toString());
            ps.executeUpdate();
        }
    }

    public List<String> findWaitingForBook(int bookId) throws SQLException {
        List<String> result = new ArrayList<>();
        String sql = """
            SELECT r.id, r.user_id, u.name, r.reserved_at
            FROM reservations r JOIN users u ON r.user_id=u.id
            WHERE r.book_id=? AND r.status='WAITING'
            ORDER BY r.id
            """;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.add("#" + rs.getInt("id") + " | User " + rs.getInt("user_id")
                            + " | " + rs.getString("name") + " | " + rs.getString("reserved_at"));
                }
            }
        }
        return result;
    }
}
