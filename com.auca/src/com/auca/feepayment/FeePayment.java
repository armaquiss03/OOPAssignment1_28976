package com.auca.feepayment;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.student.Student;
import com.auca.util.InputValidator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class FeePayment implements Identifiable, Registrable {

    private String paymentId;
    private Student student;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String paymentMethod;
    private String receiptNumber;

    public FeePayment(String paymentId, Student student, BigDecimal amount, LocalDate paymentDate,
                      String paymentMethod, String receiptNumber) {
        this.paymentId = paymentId;
        this.student = student;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.receiptNumber = receiptNumber;
    }

    @Override
    public String getId() { return this.paymentId; }

    @Override
    public String getDisplayName() { return "Receipt #" + receiptNumber + " (" + amount + ")"; }

    @Override
    public boolean isActive() { return true; }

    @Override
    public void validate() throws ValidationException {
        InputValidator.validateId(this.paymentId, "Payment ID");
        if (this.student == null) {
            throw new ValidationException("FeePayment must reference a valid Student.");
        }
        if (this.amount == null || this.amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Payment amount must be strictly greater than zero.");
        }
        InputValidator.validateNotFutureDate(this.paymentDate, "Payment date");
        InputValidator.validateAllowedValue(this.paymentMethod, List.of("CASH", "BANK_TRANSFER", "MOBILE_MONEY", "CARD"), "Payment method");
        InputValidator.validateNonBlank(this.receiptNumber, "Receipt number");
    }

    @Override
    public void printDetails() { System.out.println(this.toString()); }

    @Override
    public String getRegistrationSummary() { return "[" + paymentId + "] " + student.getDisplayName() + " - " + getDisplayName(); }

    @Override
    public String toString() {
        return "Payment ID: " + paymentId + " | Student: " + student.getDisplayName() + " | Amount: " + amount + " | Method: " + paymentMethod + " | Receipt: " + receiptNumber + " | Date: " + paymentDate;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    
}