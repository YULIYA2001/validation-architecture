package org.example.testvalidation.validation.validators.base;

import org.example.testvalidation.validation.core.api.BusinessValidator;
import org.example.testvalidation.validation.core.api.ConditionalValidator;
import org.example.testvalidation.validation.error.dto.ValidationErrorMarker;

public abstract class AbstractValidator<E extends ValidationErrorMarker>
        implements BusinessValidator<E>, ConditionalValidator {
}
