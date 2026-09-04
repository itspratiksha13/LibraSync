package com.librasync;

public record Reservation(int id, int userId, int bookId, String status, String reservedAt) {
}
