package com.auca.enrollment;

import com.auca.course.Course;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.student.Student;
import com.auca.util.InputValidator;
import java.time.LocalDate;
import java.util.List;

public class Enrollment implements Identifiable, Registrable {

    private String enrollmentId;
    private Student student;
    private Course course;
    private LocalDate enrollmentDate;
    private String semester;
    private String enrollmentStatus;

    public Enrollment(String enrollmentId, Student student, Course course, LocalDate enrollmentDate,
                      String semester, String enrollmentStatus) {
        this.enrollmentId = enrollmentId;
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
        this.semester = semester;
        this.enrollmentStatus = enrollmentStatus;
    }

    @Override
    public String getId() { return this.enrollmentId; }

    @Override
    public String getDisplayName() { return student.getDisplayName() + " -> " + course.getCourseCode(); }

    @Override
    public boolean isActive() { return "ACTIVE".equalsIgnoreCase(this.enrollmentStatus); }

    @Override
    public void validate() throws ValidationException {
        InputValidator.validateId(this.enrollmentId, "Enrollment ID");
        if (this.student == null || this.course == null) {
            throw new ValidationException("Enrollment must reference a valid Student and Course.");
        }
        InputValidator.validateNotFutureDate(this.enrollmentDate, "Enrollment date");
        InputValidator.validateNonBlank(this.semester, "Semester");
        InputValidator.validateAllowedValue(this.enrollmentStatus, List.of("ACTIVE", "DROPPED", "COMPLETED"), "Enrollment status");
    }

    @Override
    public void printDetails() { System.out.println(this.toString()); }

    @Override
    public String getRegistrationSummary() { return "[" + enrollmentId + "] " + getDisplayName() + " (" + semester + ")"; }

    @Override
    public String toString() {
        return "Enrollment ID: " + enrollmentId + " | Student: " + student.getDisplayName() + " | Course: " + course.getDisplayName() + " | Sem: " + semester + " | Status: " + enrollmentStatus;
    }

    public String getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(String enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(String enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    
}