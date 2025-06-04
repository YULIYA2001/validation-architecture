package org.example.testvalidation.validation.error.dto;

import java.util.List;

/**
 * DTO, представляющий ошибки валидации, сгруппированные по строке (например, при работе с csv-файлами).
 * <p>
 * Содержит номер строки и список ошибок уровня поля {@link ValidationErrorFieldDto}, обнаруженных в ней.
 * </p>
 */
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
