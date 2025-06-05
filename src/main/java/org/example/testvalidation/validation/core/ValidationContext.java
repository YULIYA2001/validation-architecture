package org.example.testvalidation.validation.core;

import java.util.Set;
import org.example.testvalidation.validation.core.api.BusinessValidator;
import org.example.testvalidation.validation.core.api.ConditionalValidator;
import org.example.testvalidation.validation.core.api.ValidationContextKeys;

public class ValidationContext {
    private final Set<ValidationContextKeys> validationKeys;

    public ValidationContext(Set<ValidationContextKeys> validationKeys) {
        this.validationKeys = validationKeys;
    }

    public static ValidationContext of(ValidationContextKeys... keys) {
        return new ValidationContext(Set.of(keys));
    }

    public boolean allows(BusinessValidator<?> validator) {
        if (validator instanceof ConditionalValidator conditional) {
            return conditional.getKeys().stream().anyMatch(validationKeys::contains);
        }
        return true; // если не ConditionalValidator — допускаем по умолчанию
    }
}
