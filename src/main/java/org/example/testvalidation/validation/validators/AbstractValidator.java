package org.example.testvalidation.validation.validators;

import org.example.testvalidation.validation.core.BusinessValidator;
import org.example.testvalidation.validation.error.dto.ValidationErrorMarker;

public abstract class AbstractValidator<E extends ValidationErrorMarker> implements BusinessValidator<E> {
    @Override
    public boolean supports(Object dto) {
        return true; // по умолчанию применяется ко всем — переопредели в потомках
    }
}
