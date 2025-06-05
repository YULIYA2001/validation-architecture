package org.example.testvalidation.validation.core;

import java.util.List;
import org.example.testvalidation.validation.error.dto.ValidationErrorMarker;
import org.springframework.stereotype.Component;

@Component
public class ValidatorChainFactory {
    private final ValidatorRegistry registry;

    public ValidatorChainFactory(ValidatorRegistry registry) {
        this.registry = registry;
    }

    public <T, E extends ValidationErrorMarker> BusinessValidator<T, E> buildValidatorFor(Class<T> dtoClass) {
        List<BusinessValidator<T, E>> validators = registry.getValidatorsFor(dtoClass);
        return new CompositeValidator<>(validators);
    }
}