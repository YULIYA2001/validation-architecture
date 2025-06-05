package org.example.testvalidation.validation.core;

import org.example.testvalidation.validation.error.dto.ValidationErrorMarker;

public interface BusinessValidator<T, E extends ValidationErrorMarker> {
    ValidationResult<E> validate(T dto);
    Class<T> targetType();
}
