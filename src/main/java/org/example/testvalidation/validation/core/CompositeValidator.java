package org.example.testvalidation.validation.core;

import java.util.List;
import org.example.testvalidation.validation.core.api.BusinessValidator;
import org.example.testvalidation.validation.core.api.ValidationResult;
import org.example.testvalidation.validation.error.dto.ValidationErrorMarker;

public class CompositeValidator<E extends ValidationErrorMarker> implements BusinessValidator<E> {
    private final List<BusinessValidator<E>> validators;

    public CompositeValidator(List<BusinessValidator<E>> validators) {
        this.validators = validators;
    }

    @Override
    public ValidationResult<E> validate(Object dto) {
        ValidationResult<E> result = ValidationResult.ok();
        for (BusinessValidator<E> validator : validators) {
            result.merge(validator.validate(dto));
        }
        return result;
    }

}
