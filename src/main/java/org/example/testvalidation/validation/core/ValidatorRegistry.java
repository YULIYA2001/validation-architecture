package org.example.testvalidation.validation.core;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import org.example.testvalidation.validation.error.dto.ValidationErrorMarker;
import org.springframework.stereotype.Component;

@Component
public class ValidatorRegistry {
    private final Map<Class<?>, List<BusinessValidator<?, ?>>> validators = new ConcurrentHashMap<>();

    public <T> void register(BusinessValidator<T, ?> validator) {
        validators
                .computeIfAbsent(validator.targetType(), k -> new ArrayList<>())
                .add(validator);
    }

    @SuppressWarnings("unchecked")
    public <T, E extends ValidationErrorMarker> List<BusinessValidator<T, E>> getValidatorsFor(Class<T> type) {
        List<BusinessValidator<T, E>> result = new ArrayList<>();
        for (Map.Entry<Class<?>, List<BusinessValidator<?, ?>>> entry : validators.entrySet()) {
            if (entry.getKey().isAssignableFrom(type)) {
                result.addAll((List<BusinessValidator<T, E>>) (List<?>) entry.getValue());
            }
        }
        return result;
    }
}
