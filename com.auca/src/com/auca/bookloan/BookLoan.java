package com.auca.bookloan;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.librarybook.LibraryBook;
import com.auca.student.Student;
import com.auca.util.InputValidator;
import java.time.LocalDate;

public class BookLoan implements Identifiable, Registrable {

    private String loanId;
    private Student student;
    private LibraryBook book;
    private LocalDate borrowedDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;

    public BookLoan(String loanId, Student student, LibraryBook book, LocalDate borrowedDate,
                    LocalDate dueDate, LocalDate returnedDate) {
        this.loanId = loanId;
        this.student = student;
        this.book = book;
        this.borrowedDate = borrowedDate;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;
    }

    @Override
    public String getId() { return this.loanId; }

    @Override
    public String getDisplayName() { return book.getTitle() + " (Borrowed by " + student.getDisplayName() + ")"; }

    @Override
    public boolean isActive() { return this.returnedDate == null; }

    @Override
    public void validate() throws ValidationException {
        InputValidator.validateId(this.loanId, "Loan ID");
        if (this.student == null || this.book == null) {
            throw new ValidationException("BookLoan must reference both a valid Student and LibraryBook.");
        }
        if (this.borrowedDate == null || this.dueDate == null) {
            throw new ValidationException("Borrowed date and due date are required.");
        }
        if (!this.dueDate.isAfter(this.borrowedDate)) {
            throw new ValidationException("Due date must be strictly after borrowed date.");
        }
        if (this.returnedDate != null && this.returnedDate.isBefore(this.borrowedDate)) {
            throw new ValidationException("Returned date cannot be before borrowed date.");
        }
    }

    @Override
    public void printDetails() { System.out.println(this.toString()); }

    @Override
    public String getRegistrationSummary() {
        String status = (returnedDate == null) ? "Due: " + dueDate : "Returned: " + returnedDate;
        return "[" + loanId + "] " + getDisplayName() + " [" + status + "]";
    }

    @Override
    public String toString() {
        return "Loan ID: " + loanId + " | Student: " + student.getDisplayName() + " | Book: " + book.getTitle() + " | Borrowed: " + borrowedDate + " | Due: " + dueDate + " | Returned: " + (returnedDate != null ? returnedDate : "Not yet");
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LibraryBook getBook() {
        return book;
    }

    public void setBook(LibraryBook book) {
        this.book = book;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(LocalDate borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    
}