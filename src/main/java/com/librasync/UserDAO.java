package com.librasync;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public User authenticate(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("id"), rs.getString("name"),
                            rs.getString("username"), rs.getString("password"),
                            rs.getString("role"));
                }
            }
        }
        return null;
    }

    public void addUser(String name, String username, String password, String role) throws SQLException {
        String sql = "INSERT INTO users(name, username, password, role) VALUES(?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name); ps.setString(2, username);
            ps.setString(3, password); ps.setString(4, role);
            ps.executeUpdate();
        }
    }

    public List<User> findAll() throws SQLException {
        List<User> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM users ORDER BY id");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new User(rs.getInt("id"), rs.getString("name"),
                        rs.getString("username"), rs.getString("password"),
                        rs.getString("role")));
            }
        }
        return list;
    }
}
