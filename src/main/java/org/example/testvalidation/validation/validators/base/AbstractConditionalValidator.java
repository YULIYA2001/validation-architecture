package org.example.testvalidation.validation.validators.base;

import org.example.testvalidation.validation.core.api.BusinessValidator;
import org.example.testvalidation.validation.core.api.ConditionalValidator;
import org.example.testvalidation.validation.core.api.ValidationContext;
import org.example.testvalidation.validation.error.dto.ValidationErrorMarker;

/**
 * Базовый абстрактный класс для валидаторов, реализующих бизнес-логику с возможностью условного применения
 * <p>
 *     Объединяет поведение интерфейсов {@link BusinessValidator} и {@link ConditionalValidator}, позволяя
 *     создавать валидаторы, которые могут быть активированы только при наличии соответствующего ключа
 *     в {@link ValidationContext}.
 * <p>
 *     Используется как родительский класс для конкретных валидаторов, например, для проверки полей на существование.
 */
public abstract class AbstractConditionalValidator<E extends ValidationErrorMarker>
        implements BusinessValidator<E>, ConditionalValidator {
}
