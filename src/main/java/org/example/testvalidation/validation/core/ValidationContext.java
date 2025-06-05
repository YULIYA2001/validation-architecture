package org.example.testvalidation.validation.core;

import java.util.Set;

public class ValidationContext {
    private final Set<String> validationKeys;

    public ValidationContext(Set<String> validationKeys) {
        this.validationKeys = validationKeys;
    }

    public static ValidationContext of(String... keys) {
        return new ValidationContext(Set.of(keys));
    }

    public boolean allows(BusinessValidator<?> validator) {
        if (validator instanceof ConditionalValidator conditional) {
            return conditional.getKeys().stream().anyMatch(validationKeys::contains);
        }
        return true; // если не ConditionalValidator — допускаем по умолчанию
    }
}
