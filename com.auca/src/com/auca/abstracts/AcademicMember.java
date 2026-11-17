package com.auca.abstracts;

import com.auca.department.Department;
import com.auca.exceptions.ValidationException;
import com.auca.util.InputValidator;
import java.time.LocalDate;
import java.util.List;

public abstract class AcademicMember extends Person {

    private String memberNumber;
    private Department department;
    private LocalDate joinDate;
    private String academicStatus;
    private String institutionalEmail;
    private String advisorName;

    public AcademicMember(String personId, String firstName, String lastName, String email, String phoneNumber,
                          LocalDate dateOfBirth, String memberNumber, Department department, LocalDate joinDate,
                          String academicStatus, String institutionalEmail, String advisorName) {
        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth);
        this.memberNumber = memberNumber;
        this.department = department;
        this.joinDate = joinDate;
        this.academicStatus = academicStatus;
        this.institutionalEmail = institutionalEmail;
        this.advisorName = advisorName;
    }

    public abstract String getAcademicCategory();

    @Override
    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(this.academicStatus);
    }

    @Override
    public void validate() throws ValidationException {
        super.validate();
        InputValidator.validateId(this.memberNumber, "Member number");
        if (this.department == null) {
            throw new ValidationException("Department reference cannot be null.");
        }
        InputValidator.validateNotFutureDate(this.joinDate, "Join date");
        InputValidator.validateAllowedValue(this.academicStatus, List.of("ACTIVE", "INACTIVE"), "Academic status");
        InputValidator.validateEmail(this.institutionalEmail, "Institutional email");
        InputValidator.validateName(this.advisorName, "Advisor name");
    }

    @Override
    public String toString() {
        return super.toString() + " | Member #: " + memberNumber + " | Dept: " + (department != null ? department.getDepartmentName() : "None") + " | Status: " + academicStatus;
    }

    public String getMemberNumber() {
        return memberNumber;
    }
    public void setMemberNumber(String memberNumber) {
        this.memberNumber = memberNumber;
    }

    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }
    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public String getAcademicStatus() {
        return academicStatus;
    }
    public void setAcademicStatus(String academicStatus) {
        this.academicStatus = academicStatus;
    }

    public String getInstitutionalEmail() {
        return institutionalEmail;
    }
    public void setInstitutionalEmail(String institutionalEmail) {
        this.institutionalEmail = institutionalEmail;
    }

    public String getAdvisorName() {
        return advisorName;
    }
    public void setAdvisorName(String advisorName) {
        this.advisorName = advisorName;
    }

    
}