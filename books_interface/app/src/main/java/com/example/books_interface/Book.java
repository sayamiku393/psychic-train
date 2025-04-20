package com.example.books_interface;

public class Book {
    private final String name;
    private final String author;

    Book (String name, String author) {
        this.name = name;
        this.author = author;
    }
    public String getName() {
        return this.name;
    }
    public String getAuthor() {
        return this.author;
    }
}


