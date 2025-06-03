package org.example.testvalidation.exceptions;

import java.util.Set;

// all custom exceptions in this directory (single file for single exception)

/**
 * Исключение, выбрасываемое при неудачной валидации аннотаций.
 * Содержит набор сообщений об ошибках валидации.
 */
public class FailedAnnotationValidationException extends RuntimeException {
    private final Set<String> errorMessages;

    public FailedAnnotationValidationException(Set<String> errorMessages) {
        super();
        this.errorMessages = errorMessages;
    }

    public Set<String> getErrorMessages() {
        return errorMessages;
    }
}
