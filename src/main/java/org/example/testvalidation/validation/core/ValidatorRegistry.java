package org.example.testvalidation.validation.core;

import java.util.*;
import java.util.stream.Collectors;
import org.example.testvalidation.validation.error.dto.ValidationErrorMarker;
import org.springframework.stereotype.Component;

@Component
public class ValidatorRegistry {
    private final List<BusinessValidator<?>> validators = new ArrayList<>();

    public void register(BusinessValidator<?> validator) {
        validators.add(validator);
    }

    // TODO зачем SuppressWarnings
    @SuppressWarnings("unchecked")
    public <E extends ValidationErrorMarker> List<BusinessValidator<E>> getValidatorsFor(Object dto) {

        return validators.stream()
                .filter(v -> v.supports(dto))
                .map(v -> (BusinessValidator<E>)v)
                .collect(Collectors.toList());
    }
}
