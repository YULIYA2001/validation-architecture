package org.example.testvalidation.validation.core;

import java.util.List;
import org.example.testvalidation.validation.error.dto.ValidationErrorMarker;

public class CompositeValidator<T, E extends ValidationErrorMarker> implements BusinessValidator<T, E> {
    private final List<BusinessValidator<T, E>> validators;

    public CompositeValidator(List<BusinessValidator<T, E>> validators) {
        this.validators = validators;
    }

    @Override
    public ValidationResult<E> validate(T dto) {
        ValidationResult<E> result = ValidationResult.ok();
        for (BusinessValidator<T, E> validator : validators) {
            result.merge(validator.validate(dto));
        }
        return result;
    }

    @Override
    public Class<T> targetType() {
        throw new UnsupportedOperationException("CompositeValidator doesn't expose targetType");
    }
}
