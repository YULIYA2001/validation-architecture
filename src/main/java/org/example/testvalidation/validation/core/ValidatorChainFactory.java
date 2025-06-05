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

    public <E extends ValidationErrorMarker> BusinessValidator<E> buildValidatorFor(Object dto) {
        List<BusinessValidator<E>> validators = registry.getValidatorsFor(dto);
        return new CompositeValidator<>(validators);
    }
}