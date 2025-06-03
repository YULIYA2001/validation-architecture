package org.example.testvalidation.validator.error.dto;

import java.util.List;

public class ValidationErrorRowDto implements ValidationErrorMarker {
    private final int row;
    private final List<ValidationErrorFieldDto> errorFields;


    public ValidationErrorRowDto(int row, List<ValidationErrorFieldDto> errorFields) {
        this.row = row;
        this.errorFields = errorFields;
    }

    public int getRow() {
        return row;
    }

    public List<ValidationErrorFieldDto> getErrorFields() {
        return errorFields;
    }
}
