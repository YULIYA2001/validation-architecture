package org.example.testvalidation.validation.validators;

import org.example.testvalidation.validation.core.BusinessValidator;
import org.example.testvalidation.validation.error.dto.ValidationErrorFieldDto;

public abstract class AbstractValidator<T> implements BusinessValidator<T, ValidationErrorFieldDto> {
    private final Class<T> type;

    protected AbstractValidator(Class<T> type) {
        this.type = type;
    }

    @Override
    public Class<T> targetType() {
        return type;
    }
}
