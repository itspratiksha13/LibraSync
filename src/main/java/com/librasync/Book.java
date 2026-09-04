package com.librasync;

public class Book {
    private final int id;
    private final String title;
    private final String author;
    private final String category;
    private final int totalCopies;
    private final int availableCopies;

    public Book(int id, String title, String author, String category, int totalCopies, int availableCopies) {
        this.id = id; this.title = title; this.author = author; this.category = category;
        this.totalCopies = totalCopies; this.availableCopies = availableCopies;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }

    @Override
    public String toString() {
        return String.format("%d | %-30s | %-22s | %-14s | %d/%d available",
                id, title, author, category, availableCopies, totalCopies);
    }
}
