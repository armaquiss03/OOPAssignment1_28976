package com.auca.util;

import com.auca.exceptions.ValidationException;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class InputValidator {

    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    private static final String PHONE_REGEX = "^\\+?[0-9]{10,15}$";

    public static int parseInt(String input, String fieldName) throws ValidationException {
        if (input == null || input.trim().isEmpty()) {
            throw new ValidationException(fieldName + " cannot be blank.");
        }
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException(fieldName + " must be a valid whole number.");
        }
    }

    public static double parseDouble(String input, String fieldName) throws ValidationException {
        if (input == null || input.trim().isEmpty()) {
            throw new ValidationException(fieldName + " cannot be blank.");
        }
        try {
            return Double.parseDouble(input.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException(fieldName + " must be a valid decimal number.");
        }
    }

    public static BigDecimal parseMoney(String input, String fieldName) throws ValidationException {
        if (input == null || input.trim().isEmpty()) {
            throw new ValidationException(fieldName + " cannot be blank.");
        }
        try {
            BigDecimal amount = new BigDecimal(input.trim());
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new ValidationException(fieldName + " must be strictly greater than zero.");
            }
            return amount;
        } catch (NumberFormatException e) {
            throw new ValidationException(fieldName + " must be a valid monetary amount (e.g., 50000.00).");
        }
    }

    public static LocalDate parseDate(String input, String fieldName) throws ValidationException {
        if (input == null || input.trim().isEmpty()) {
            throw new ValidationException(fieldName + " cannot be blank.");
        }
        try {
            return LocalDate.parse(input.trim()); // Expects YYYY-MM-DD
        } catch (DateTimeParseException e) {
            throw new ValidationException(fieldName + " is invalid. Use format YYYY-MM-DD (e.g., 2004-05-16).");
        }
    }

    public static String validateNonBlank(String value, String fieldName) throws ValidationException {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException(fieldName + " cannot be blank.");
        }
        return value.trim();
    }

    public static String validateName(String name, String fieldName) throws ValidationException {
        String trimmed = validateNonBlank(name, fieldName);
        if (trimmed.length() < Registrable.MIN_NAME_LENGTH || trimmed.length() > Registrable.MAX_NAME_LENGTH) {
            throw new ValidationException(fieldName + " must be between " 
                + Registrable.MIN_NAME_LENGTH + " and " + Registrable.MAX_NAME_LENGTH + " characters.");
        }
        return trimmed;
    }

    public static String validateId(String id, String fieldName) throws ValidationException {
        String trimmed = validateNonBlank(id, fieldName);
        if (trimmed.length() < Identifiable.MIN_ID_LENGTH || trimmed.length() > Identifiable.MAX_ID_LENGTH) {
            throw new ValidationException(fieldName + " must be between " 
                + Identifiable.MIN_ID_LENGTH + " and " + Identifiable.MAX_ID_LENGTH + " characters.");
        }
        return trimmed;
    }

    public static String validateEmail(String email, String fieldName) throws ValidationException {
        String trimmed = validateNonBlank(email, fieldName);
        if (trimmed.length() > Registrable.MAX_EMAIL_LENGTH || !trimmed.matches(EMAIL_REGEX)) {
            throw new ValidationException(fieldName + " must be a valid email address (max " 
                + Registrable.MAX_EMAIL_LENGTH + " chars).");
        }
        return trimmed;
    }

    public static String validatePhone(String phone, String fieldName) throws ValidationException {
        String trimmed = validateNonBlank(phone, fieldName);
        if (!trimmed.matches(PHONE_REGEX)) {
            throw new ValidationException(fieldName + " must contain 10 to 15 digits with an optional leading '+'.");
        }
        return trimmed;
    }

    public static void validateRange(int value, int min, int max, String fieldName) throws ValidationException {
        if (value < min || value > max) {
            throw new ValidationException(fieldName + " must be between " + min + " and " + max + ".");
        }
    }

    public static void validatePositive(int value, String fieldName) throws ValidationException {
        if (value <= 0) {
            throw new ValidationException(fieldName + " must be greater than zero.");
        }
    }

    public static void validateNonNegative(int value, String fieldName) throws ValidationException {
        if (value < 0) {
            throw new ValidationException(fieldName + " cannot be negative.");
        }
    }

    public static void validatePastDate(LocalDate date, String fieldName) throws ValidationException {
        if (date == null || !date.isBefore(LocalDate.now())) {
            throw new ValidationException(fieldName + " must be strictly in the past.");
        }
    }

    public static void validateNotFutureDate(LocalDate date, String fieldName) throws ValidationException {
        if (date == null || date.isAfter(LocalDate.now())) {
            throw new ValidationException(fieldName + " cannot be in the future.");
        }
    }

    public static void validateAllowedValue(String value, List<String> allowedValues, String fieldName) throws ValidationException {
        String trimmed = validateNonBlank(value, fieldName).toUpperCase();
        if (!allowedValues.contains(trimmed)) {
            throw new ValidationException(fieldName + " must be one of: " + String.join(", ", allowedValues));
        }
    }
}