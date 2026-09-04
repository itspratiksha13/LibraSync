package com.librasync;

public class User {
    private final int id;
    private final String name;
    private final String username;
    private final String password;
    private final String role;

    public User(int id, String name, String username, String password, String role) {
        this.id = id; this.name = name; this.username = username;
        this.password = password; this.role = role;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    @Override
    public String toString() {
        return id + " | " + name + " | " + username + " | " + role;
    }
}
