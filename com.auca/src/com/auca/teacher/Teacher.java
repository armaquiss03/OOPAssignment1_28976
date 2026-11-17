package com.auca.teacher;

import com.auca.abstracts.AcademicMember;
import com.auca.department.Department;
import com.auca.exceptions.ValidationException;
import com.auca.util.InputValidator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Teacher extends AcademicMember {

    private String employeeNumber;
    private String specialization;
    private LocalDate hireDate;
    private BigDecimal salary;
    private String officeNumber;
    private String employmentType;

    public Teacher(String personId, String firstName, String lastName, String email, String phoneNumber,
                   LocalDate dateOfBirth, String memberNumber, Department department, LocalDate joinDate,
                   String academicStatus, String institutionalEmail, String advisorName,
                   String employeeNumber, String specialization, LocalDate hireDate, BigDecimal salary,
                   String officeNumber, String employmentType) {
        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth, memberNumber, department, joinDate, academicStatus, institutionalEmail, advisorName);
        this.employeeNumber = employeeNumber;
        this.specialization = specialization;
        this.hireDate = hireDate;
        this.salary = salary;
        this.officeNumber = officeNumber;
        this.employmentType = employmentType;
    }

    @Override
    public String getAcademicCategory() { return "Academic Faculty"; }

    @Override
    public String getRoleDescription() { return "Teacher (" + this.specialization + ")"; }

    @Override
    public void validate() throws ValidationException {
        super.validate();
        InputValidator.validateId(this.employeeNumber, "Employee number");
        InputValidator.validateNonBlank(this.specialization, "Specialization");
        InputValidator.validateNotFutureDate(this.hireDate, "Hire date");
        if (this.salary == null || this.salary.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Salary must be strictly greater than zero.");
        }
        InputValidator.validateNonBlank(this.officeNumber, "Office number");
        InputValidator.validateAllowedValue(this.employmentType, List.of("FULL_TIME", "PART_TIME", "VISITING"), "Employment type");
    }

    @Override
    public String toString() {
        return super.toString() + " | Emp #: " + employeeNumber + " | Spec: " + specialization + " | Office: " + officeNumber;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

}