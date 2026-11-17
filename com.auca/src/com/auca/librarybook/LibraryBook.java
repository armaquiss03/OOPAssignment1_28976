package com.auca.librarybook;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.util.InputValidator;

public class LibraryBook implements Identifiable, Registrable {

    private String bookId;
    private String isbn;
    private String title;
    private String author;
    private int totalCopies;
    private int availableCopies;

    public LibraryBook(String bookId, String isbn, String title, String author, int totalCopies, int availableCopies) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    @Override
    public String getId() { return this.bookId; }

    @Override
    public String getDisplayName() { return title + " by " + author; }

    @Override
    public boolean isActive() { return this.availableCopies > 0; }

    @Override
    public void validate() throws ValidationException {
        InputValidator.validateId(this.bookId, "Book ID");
        InputValidator.validateNonBlank(this.isbn, "ISBN");
        InputValidator.validateNonBlank(this.title, "Book title");
        InputValidator.validateName(this.author, "Author");
        InputValidator.validateNonNegative(this.totalCopies, "Total copies");
        InputValidator.validateNonNegative(this.availableCopies, "Available copies");
        if (this.availableCopies > this.totalCopies) {
            throw new ValidationException("Available copies (" + availableCopies + ") cannot exceed total copies (" + totalCopies + ").");
        }
    }

    @Override
    public void printDetails() { System.out.println(this.toString()); }

    @Override
    public String getRegistrationSummary() { return "[" + bookId + "] " + getDisplayName() + " (" + availableCopies + "/" + totalCopies + " available)"; }

    @Override
    public String toString() {
        return "Book ID: " + bookId + " | ISBN: " + isbn + " | Title: " + title + " | Author: " + author + " | Stock: " + availableCopies + "/" + totalCopies;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    
}