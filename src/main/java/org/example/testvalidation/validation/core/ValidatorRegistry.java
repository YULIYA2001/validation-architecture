package org.example.testvalidation.validation.core;

import java.util.*;
import org.example.testvalidation.validation.error.dto.ValidationErrorMarker;
import org.springframework.stereotype.Component;

@Component
public class ValidatorRegistry {
    private final List<BusinessValidator<?>> validators = new ArrayList<>();

    public void register(BusinessValidator<?> validator) {
        validators.add(validator);
    }

    public <E extends ValidationErrorMarker> List<BusinessValidator<E>> getValidatorsFor(ValidationContext context) {
        return validators.stream()
                .filter(context::allows) // добавили фильтр по контексту
                .map(v -> (BusinessValidator<E>) v)
                .toList();
    }
}
