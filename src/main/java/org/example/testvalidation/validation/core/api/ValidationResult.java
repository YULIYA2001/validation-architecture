package org.example.testvalidation.validation.core.api;

import java.util.ArrayList;
import java.util.List;
import org.example.testvalidation.validation.error.dto.ValidationErrorMarker;

/**
 * Класс, представляющий результат валидации
 */
public class ValidationResult<E extends ValidationErrorMarker> {
    private final List<E> errors = new ArrayList<>();

    private ValidationResult(){}

    /**
     * Возвращает успешный результат валидации (без ошибок)
     */
    public static <E extends ValidationErrorMarker> ValidationResult<E> ok() {
        return new ValidationResult<>();
    }

    public void addError(E error) {
        errors.add(error);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public List<E> getErrors() {
        return errors;
    }

    public void merge(ValidationResult<E> other) {
        errors.addAll(other.getErrors());
    }
}
