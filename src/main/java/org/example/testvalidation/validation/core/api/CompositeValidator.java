package org.example.testvalidation.validation.core.api;

import java.util.List;

import org.example.testvalidation.validation.error.dto.ValidationErrorMarker;

/**
 * Цепочка валидаторов, объединяющая несколько {@link BusinessValidator}-ов
 * <p>
 *     Позволяет последовательно применить цепочку валидаторов к одному объекту и объединить результаты валидации.
 */
public class CompositeValidator<E extends ValidationErrorMarker> implements BusinessValidator<E> {
    private final List<BusinessValidator<E>> validators;

    public CompositeValidator(List<BusinessValidator<E>> validators) {
        this.validators = validators;
    }

    /**
     * Последовательно выполняет валидацию всеми валидаторами цепочки.
     * <p> Все ошибки объединяются в один {@link ValidationResult}
     */
    @Override
    public ValidationResult<E> validate(Object dto) {
        ValidationResult<E> result = ValidationResult.ok();
        for (BusinessValidator<E> validator : validators) {
            result.merge(validator.validate(dto));
        }
        return result;
    }
}
