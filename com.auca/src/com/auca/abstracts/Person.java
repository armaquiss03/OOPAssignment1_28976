package com.auca.abstracts;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.util.InputValidator;
import java.time.LocalDate;

public abstract class Person implements Identifiable, Registrable {

    private String personId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;

    public Person(String personId, String firstName, String lastName, String email, String phoneNumber, LocalDate dateOfBirth) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    

    public String getPersonId() {
        return personId;
    }
    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }



    @Override
    public String getId() { return this.personId; }

    @Override
    public String getDisplayName() { return this.firstName + " " + this.lastName; }

    @Override
    public String getRegistrationSummary() {
        return "[" + personId + "] " + getDisplayName() + " - " + getRoleDescription();
    }

    @Override
    public void printDetails() { System.out.println(this.toString()); }

    @Override
    public void validate() throws ValidationException {
        InputValidator.validateId(this.personId, "Person ID");
        InputValidator.validateName(this.firstName, "First name");
        InputValidator.validateName(this.lastName, "Last name");
        InputValidator.validateEmail(this.email, "Email");
        InputValidator.validatePhone(this.phoneNumber, "Phone number");
        InputValidator.validatePastDate(this.dateOfBirth, "Date of birth");
    }

    public abstract String getRoleDescription();

    @Override
    public String toString() {
        return "ID: " + personId + " | Name: " + getDisplayName() + " | Email: " + email + " | Phone: " + phoneNumber + " | DOB: " + dateOfBirth;
    }

    // Keep your existing getters and setters here
}