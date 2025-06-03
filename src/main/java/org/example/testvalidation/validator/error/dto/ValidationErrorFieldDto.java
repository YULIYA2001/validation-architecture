package org.example.testvalidation.validator.error.dto;

public class ValidationErrorFieldDto implements ValidationErrorMarker {
    private final String field;
    private final Object invalidValue;
    private final String message;

    public ValidationErrorFieldDto(String field, Object invalidValue, String message) {
        this.field = field;
        this.invalidValue = invalidValue;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public Object getInvalidValue() {
        return invalidValue;
    }

    public String getMessage() {
        return message;
    }
}
