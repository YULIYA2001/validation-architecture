package org.example.testvalidation.validation.core;

import java.util.*;
import org.example.testvalidation.validation.core.api.BusinessValidator;
import org.example.testvalidation.validation.core.api.ValidationContext;
import org.example.testvalidation.validation.core.config.ValidatorAutoConfiguration;
import org.example.testvalidation.validation.error.dto.ValidationErrorMarker;
import org.springframework.stereotype.Component;

/**
 * Реестр бизнес-валидаторов, доступных в приложении
 * <p>
 *     Позволяет регистрировать валидаторы и извлекать их по заданному {@link ValidationContext}.
 *     Используется для построения цепочек валидации через {@link ValidatorChainFactory}.
 * <p>
 *     Регистрация происходит автоматически в {@link ValidatorAutoConfiguration}.
 */
@Component
public class ValidatorRegistry {
    private final List<BusinessValidator<?>> validators = new ArrayList<>();

    public void register(BusinessValidator<?> validator) {
        validators.add(validator);
    }

    public <E extends ValidationErrorMarker> List<BusinessValidator<E>> getValidatorsFor(ValidationContext context) {
        return validators.stream()
                .filter(context::allows)
                .map(v -> (BusinessValidator<E>) v)
                .toList();
    }
}
