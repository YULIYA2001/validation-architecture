package org.example.testvalidation.validation.core;

import org.example.testvalidation.validation.error.dto.ValidationErrorMarker;

public interface BusinessValidator<E extends ValidationErrorMarker> {
    ValidationResult<E> validate(Object dto);
}
