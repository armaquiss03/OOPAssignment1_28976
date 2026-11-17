package com.auca.course;

import com.auca.department.Department;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.teacher.Teacher;
import com.auca.util.InputValidator;

public class Course implements Identifiable, Registrable {

    private String courseId;
    private String courseCode;
    private String courseTitle;
    private int creditHours;
    private Department department;
    private Teacher assignedTeacher;

    public Course(String courseId, String courseCode, String courseTitle, int creditHours,
                  Department department, Teacher assignedTeacher) {
        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.creditHours = creditHours;
        this.department = department;
        this.assignedTeacher = assignedTeacher;
    }

    @Override
    public String getId() { return this.courseId; }

    @Override
    public String getDisplayName() { return courseCode + " - " + courseTitle; }

    @Override
    public boolean isActive() { return true; }

    @Override
    public void validate() throws ValidationException {
        InputValidator.validateId(this.courseId, "Course ID");
        InputValidator.validateNonBlank(this.courseCode, "Course code");
        InputValidator.validateNonBlank(this.courseTitle, "Course title");
        InputValidator.validateRange(this.creditHours, 1, 6, "Credit hours");
        if (this.department == null || this.assignedTeacher == null) {
            throw new ValidationException("Course must reference a valid Department and Teacher.");
        }
    }

    @Override
    public void printDetails() { System.out.println(this.toString()); }

    @Override
    public String getRegistrationSummary() { return "[" + courseId + "] " + getDisplayName() + " (" + creditHours + " credits)"; }

    @Override
    public String toString() {
        return "Course ID: " + courseId + " | " + getDisplayName() + " | Credits: " + creditHours + " | Dept: " + department.getDepartmentName() + " | Lecturer: " + assignedTeacher.getDisplayName();
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Teacher getAssignedTeacher() {
        return assignedTeacher;
    }

    public void setAssignedTeacher(Teacher assignedTeacher) {
        this.assignedTeacher = assignedTeacher;
    }

    
}