package com.auca.exceptions;

public class PrerequisiteMissingException extends Exception {
    public PrerequisiteMissingException(String message) {
        super(message);
    }

    public PrerequisiteMissingException(String message, Throwable cause) {
        super(message, cause);
    }
}