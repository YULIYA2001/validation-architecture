package org.example.testvalidation.exceptions;

import java.util.List;
import org.example.testvalidation.validation.error.dto.ValidationErrorMarker;

/**
 * Исключение, выбрасываемое при неудачной валидации аннотаций
 * <p>
 * Содержит список ошибок валидации, которые могут относиться как к отдельным полям,
 * так и к целым объектам (строкам csv).
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
