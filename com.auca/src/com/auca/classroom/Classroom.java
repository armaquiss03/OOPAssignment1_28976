package com.auca.classroom;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.util.InputValidator;

public class Classroom implements Identifiable, Registrable {

    private String classroomId;
    private String buildingName;
    private String roomNumber;
    private int capacity;
    private int floorNumber;
    private boolean available;

    // Constructor chaining example (defaults available to true)
    public Classroom(String classroomId, String buildingName, String roomNumber, int capacity, int floorNumber) {
        this(classroomId, buildingName, roomNumber, capacity, floorNumber, true);
    }

    public Classroom(String classroomId, String buildingName, String roomNumber, int capacity, int floorNumber, boolean available) {
        this.classroomId = classroomId;
        this.buildingName = buildingName;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.floorNumber = floorNumber;
        this.available = available;
    }

    @Override
    public String getId() { return this.classroomId; }

    @Override
    public String getDisplayName() { return buildingName + " - Room " + roomNumber; }

    @Override
    public boolean isActive() { return this.available; }

    @Override
    public void validate() throws ValidationException {
        InputValidator.validateId(this.classroomId, "Classroom ID");
        InputValidator.validateNonBlank(this.buildingName, "Building name");
        InputValidator.validateNonBlank(this.roomNumber, "Room number");
        InputValidator.validatePositive(this.capacity, "Capacity");
        InputValidator.validateNonNegative(this.floorNumber, "Floor number");
    }

    @Override
    public void printDetails() { System.out.println(this.toString()); }

    @Override
    public String getRegistrationSummary() { return "[" + classroomId + "] " + getDisplayName() + " (Cap: " + capacity + ")"; }

    @Override
    public String toString() {
        return "Classroom ID: " + classroomId + " | " + getDisplayName() + " | Floor: " + floorNumber + " | Cap: " + capacity + " | Available: " + (available ? "YES" : "NO");
    }

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    
}