package com.auca.administrator;

import com.auca.abstracts.Person;
import com.auca.exceptions.ValidationException;
import com.auca.util.InputValidator;
import java.time.LocalDate;

public class Administrator extends Person {

    private String staffNumber;
    private String jobTitle;
    private String assignedOffice;
    private LocalDate employmentDate;
    private int accessLevel;
    private boolean active;

    public Administrator(String personId, String firstName, String lastName, String email, String phoneNumber,
                         LocalDate dateOfBirth, String staffNumber, String jobTitle, String assignedOffice,
                         LocalDate employmentDate, int accessLevel, boolean active) {
        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth);
        this.staffNumber = staffNumber;
        this.jobTitle = jobTitle;
        this.assignedOffice = assignedOffice;
        this.employmentDate = employmentDate;
        this.accessLevel = accessLevel;
        this.active = active;
    }

    @Override
    public boolean isActive() { return this.active; }

    @Override
    public String getRoleDescription() { return "Administrator (" + this.jobTitle + ")"; }

    @Override
    public void validate() throws ValidationException {
        super.validate();
        InputValidator.validateId(this.staffNumber, "Staff number");
        InputValidator.validateNonBlank(this.jobTitle, "Job title");
        InputValidator.validateNonBlank(this.assignedOffice, "Assigned office");
        InputValidator.validateNotFutureDate(this.employmentDate, "Employment date");
        InputValidator.validatePositive(this.accessLevel, "Access level");
    }

    @Override
    public String toString() {
        return super.toString() + " | Staff #: " + staffNumber + " | Title: " + jobTitle + " | Office: " + assignedOffice + " | Access: " + accessLevel;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getAssignedOffice() {
        return assignedOffice;
    }

    public void setAssignedOffice(String assignedOffice) {
        this.assignedOffice = assignedOffice;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    
}