package org.example.testvalidation.validation.validators.base;

import java.util.Set;
import java.util.function.Predicate;
import org.example.testvalidation.validation.core.api.ValidationContext;
import org.example.testvalidation.validation.core.api.ValidationContextKeys;
import org.example.testvalidation.validation.core.api.ValidationResult;
import org.example.testvalidation.validation.error.dto.ValidationErrorFieldDto;
import org.example.testvalidation.validation.utils.FieldExtractor;
import org.example.testvalidation.validation.utils.ValidationMessages;

/**
 * Универсальный абстрактный валидатор - существование значения поля в источнике данных по заданному условию
 * <p>
 *     Основан на ключе контекста {@link ValidationContextKeys}, который указывает имя поля в DTO и определяет,
 *     должен ли валидатор быть активирован в конкретном {@link ValidationContext}.
 * <p>
 *     Подходит для повторного использования с различными типами данных и репозиториями, принимая проверку
 *     в виде {@link Predicate}.
 */
public abstract class FieldExistsValidator<T> extends AbstractConditionalValidator<ValidationErrorFieldDto> {
    private final ValidationContextKeys key;
    private final Class<T> valueType;
    private final Predicate<T> existenceCheck;

    /**
     * Конструктор универсального валидатора существования значения
     *
     * @param key ключ контекста, определяющий, по какому имени поля искать значение
     * @param valueType тип значения поля, с которым работает валидатор
     * @param existenceCheck условие, проверяющее наличие значения в источнике данных (например, БД)
     */
    protected FieldExistsValidator(ValidationContextKeys key,
                                Class<T> valueType,
                                Predicate<T> existenceCheck) {
        this.key = key;
        this.valueType = valueType;
        this.existenceCheck = existenceCheck;
    }

    /**
     * Выполняет валидацию: извлекает значение из DTO, проверяет его тип и наличие через {@code existenceCheck}
     *
     * @param dto объект данных, в котором ищется значение
     * @return результат валидации с ошибками, если значение не найдено в источнике
     */
    @Override
    public ValidationResult<ValidationErrorFieldDto> validate(Object dto) {
        Object rawValue = FieldExtractor.findFieldByName(dto, key.getFieldName());

        ValidationResult<ValidationErrorFieldDto> result = ValidationResult.ok();

        if (valueType.isInstance(rawValue)) {
            T value = valueType.cast(rawValue);
            if (value != null && !existenceCheck.test(value)) {
                result.addError(new ValidationErrorFieldDto(
                        key.getFieldName(),
                        value,
                        ValidationMessages.NOT_FOUND_IN_DB
                ));
            }
        }

        return result;
    }

    /**
     * Возвращает контекстный ключ, необходимый для активации данного валидатора
     *
     * @return множество, содержащее один {@link ValidationContextKeys}
     */
    @Override
    public Set<ValidationContextKeys> getKeys() {
        return Set.of(key);
    }
}