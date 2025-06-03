package org.example.testvalidation.exceptions;

import java.util.List;
import org.example.testvalidation.validator.error.dto.ValidationErrorMarker;

// all custom exceptions in this directory (single file for single exception)

/**
 * Исключение, выбрасываемое при неудачной валидации аннотаций.
 * Содержит набор сообщений об ошибках валидации.
 */
public class FailedAnnotationValidationException extends RuntimeException {
    private final List<? extends ValidationErrorMarker> errors;

    public FailedAnnotationValidationException(List<? extends ValidationErrorMarker> errors) {
        super();
        this.errors = errors;
    }

    public List<? extends ValidationErrorMarker> getErrors() {
        return errors;
    }
}
