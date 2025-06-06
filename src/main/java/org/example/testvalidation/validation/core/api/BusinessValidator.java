package org.example.testvalidation.validation.core.api;

import org.example.testvalidation.validation.error.dto.ValidationErrorMarker;

/**
 * Интерфейс для всех бизнес-валидаторов
 * <p>
 *     Используется для выполнения валидации бизнес-правил на произвольных DTO-объектах.
 *
 * @param <E> тип ошибки, которая будет возвращена в результате валидации
 */
public interface BusinessValidator<E extends ValidationErrorMarker> {
    ValidationResult<E> validate(Object dto);
}
