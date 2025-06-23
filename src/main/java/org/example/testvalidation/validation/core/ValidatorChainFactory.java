package org.example.testvalidation.validation.core;

import java.util.List;
import org.example.testvalidation.validation.core.api.BusinessValidator;
import org.example.testvalidation.validation.core.api.CompositeValidator;
import org.example.testvalidation.validation.core.api.ValidationContext;
import org.example.testvalidation.validation.error.dto.ValidationErrorMarker;
import org.springframework.stereotype.Component;

/**
 * Фабрика для создания цепочек бизнес-валидаторов на основе заданного {@link ValidationContext}
 * <p>
 *     Использует {@link ValidatorRegistry} для получения подходящих валидаторов и объединяет их в единый
 *     {@link CompositeValidator}, который можно применить к объекту.
 */
@Component
public class ValidatorChainFactory {
    private final ValidatorRegistry registry;

    public ValidatorChainFactory(ValidatorRegistry registry) {
        this.registry = registry;
    }

    /**
     * Строит композитный валидатор, включающий все валидаторы, разрешённые для указанного контекста
     */
    public <E extends ValidationErrorMarker> BusinessValidator<E> buildValidatorFor(ValidationContext context) {
        List<BusinessValidator<E>> validators = registry.getValidatorsFor(context);
        return new CompositeValidator<>(validators);
    }
}