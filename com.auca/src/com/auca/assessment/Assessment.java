package com.auca.assessment;

import com.auca.enrollment.Enrollment;
import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.util.InputValidator;
import java.time.LocalDate;
import java.util.List;

public class Assessment implements Identifiable, Registrable {

    private String assessmentId;
    private Enrollment enrollment;
    private String assessmentType;
    private double maximumScore;
    private double obtainedScore;
    private LocalDate assessmentDate;

    public Assessment(String assessmentId, Enrollment enrollment, String assessmentType,
                      double maximumScore, double obtainedScore, LocalDate assessmentDate) {
        this.assessmentId = assessmentId;
        this.enrollment = enrollment;
        this.assessmentType = assessmentType;
        this.maximumScore = maximumScore;
        this.obtainedScore = obtainedScore;
        this.assessmentDate = assessmentDate;
    }

    @Override
    public String getId() { return this.assessmentId; }

    @Override
    public String getDisplayName() { return assessmentType + " (" + obtainedScore + "/" + maximumScore + ")"; }

    @Override
    public boolean isActive() { return enrollment != null && enrollment.isActive(); }

    @Override
    public void validate() throws ValidationException {
        InputValidator.validateId(this.assessmentId, "Assessment ID");
        if (this.enrollment == null) {
            throw new ValidationException("Assessment must reference an existing Enrollment.");
        }
        if (!this.enrollment.isActive()) {
            throw new ValidationException("Cannot record assessment for an inactive enrollment.");
        }
        InputValidator.validateAllowedValue(this.assessmentType, List.of("QUIZ", "ASSIGNMENT", "MIDTERM", "FINAL"), "Assessment type");
        if (this.maximumScore <= 0) {
            throw new ValidationException("Maximum score must be greater than zero.");
        }
        if (this.obtainedScore < 0 || this.obtainedScore > this.maximumScore) {
            throw new ValidationException("Obtained score must be between 0 and " + this.maximumScore + ".");
        }
        InputValidator.validateNotFutureDate(this.assessmentDate, "Assessment date");
    }

    @Override
    public void printDetails() { System.out.println(this.toString()); }

    @Override
    public String getRegistrationSummary() { return "[" + assessmentId + "] " + enrollment.getDisplayName() + " - " + getDisplayName(); }

    @Override
    public String toString() {
        return "Assessment ID: " + assessmentId + " | " + enrollment.getDisplayName() + " | Type: " + assessmentType + " | Score: " + obtainedScore + "/" + maximumScore + " | Date: " + assessmentDate;
    }

    public String getAssessmentId() {
        return assessmentId;
    }

    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public double getMaximumScore() {
        return maximumScore;
    }

    public void setMaximumScore(double maximumScore) {
        this.maximumScore = maximumScore;
    }

    public double getObtainedScore() {
        return obtainedScore;
    }

    public void setObtainedScore(double obtainedScore) {
        this.obtainedScore = obtainedScore;
    }

    public LocalDate getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(LocalDate assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    
}