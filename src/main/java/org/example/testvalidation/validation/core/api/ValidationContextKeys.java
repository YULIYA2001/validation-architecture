package org.example.testvalidation.validation.core.api;

import org.example.testvalidation.validation.validators.CitizenshipExistsConditionalValidator;

/**
 * Перечисление ключей контекста валидации
 * <p>
 *     Значения данного перечисления используются в {@link ValidationContext}, чтобы определить,
 *     какие валидаторы следует активировать при проверке объекта.
 * <p>
 *     Каждый ключ связан с конкретным именем поля в DTO-объекте.
 */
public enum ValidationContextKeys {
    /**
     * Ключ, активирующий бизнес-валидацию наличия гражданства для поля с именем {@code "citizenship"}.
     * <p>
     *     Используется, например, в {@link CitizenshipExistsConditionalValidator}
     */
    CITIZENSHIP_EXISTS(ValidationFields.CITIZENSHIP),
    FACULTY_EXISTS(ValidationFields.FACULTY),
    STUDENT(ValidationFields.NO_FIELD);

    private final ValidationFields field;

    ValidationContextKeys(ValidationFields field) {
        this.field = field;
    }

    public String getFieldName() {
        return field.getName();
    }

    public ValidationFields getField() {
        return field;
    }
}
